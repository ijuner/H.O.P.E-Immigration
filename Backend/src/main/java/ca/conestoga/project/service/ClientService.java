package ca.conestoga.project.service;

import ca.conestoga.project.common.Context;
import ca.conestoga.project.common.DocumentTypeEnum;
import ca.conestoga.project.common.ErrorEnum;
import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.dao.client.*;
import ca.conestoga.project.dao.program.ProgramDao;
import ca.conestoga.project.dao.program.ProgramScoreDao;
import ca.conestoga.project.dao.program.UserReportDao;
import ca.conestoga.project.dao.program.UserReportScoreDao;
import ca.conestoga.project.entity.client.*;
import ca.conestoga.project.entity.policy.ProgramInvitation;
import ca.conestoga.project.entity.policy.ProgramScore;
import ca.conestoga.project.product.bo.policy.ApiMyReport;
import ca.conestoga.project.product.bo.policy.ApiReportScore;
import ca.conestoga.project.product.bo.user.*;
import ca.conestoga.project.util.ChatGPTClient;
import ca.conestoga.project.util.ConditionUtils;
import ca.conestoga.project.util.PdfGenerator;
import org.springframework.beans.propertyeditors.ClassEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.lang.reflect.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for actions of Client
 */
@Service
public class ClientService {
    private final ClientDao clientDao;
    ClientBasicInfoDao basicInfoDao;
    EducationExperienceDao educationExperienceDao;
    ExtraInfoDao extraInfoDao;
    LanguageAbilityDao languageAbilityDao;
    SpouseInfoDao spouseInfoDao;
    WorkExperienceDao   workExperienceDao;
    ClientDocumentDao clientDocumentDao;
    ProgramDao programDao;
    ProgramScoreDao programScoreDao;
    UserReportDao userReportDao;
    UserReportScoreDao userReportScoreDao;

    private final PasswordEncoder passwordEncoder;
    public ClientService(ClientDao clientDao, ClientBasicInfoDao basicInfoDao, EducationExperienceDao educationExperienceDao,
                         ExtraInfoDao extraInfoDao, LanguageAbilityDao languageAbilityDao,
                         SpouseInfoDao spouseInfoDao, WorkExperienceDao workExperienceDao,
                         ClientDocumentDao clientDocumentDao, PasswordEncoder passwordEncoder,
                         ProgramDao programDao, ProgramScoreDao programScoreDao,
                         UserReportDao userReportDao, UserReportScoreDao userReportScoreDao){
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
        this.basicInfoDao = basicInfoDao;
        this.educationExperienceDao = educationExperienceDao;
        this.extraInfoDao = extraInfoDao;
        this.languageAbilityDao = languageAbilityDao;
        this.spouseInfoDao = spouseInfoDao;
        this.workExperienceDao = workExperienceDao;
        this.clientDocumentDao = clientDocumentDao;
        this.programDao = programDao;
        this.programScoreDao = programScoreDao;
        this.userReportDao = userReportDao;
        this.userReportScoreDao = userReportScoreDao;
    }
    /**
     * login to system
     * @param userEntity to register
     * @return
     */
    public RespBody<?> login(ApiUserEntity userEntity) {
        Client client = clientDao.findByUserNameOrEmail(userEntity.getName(), userEntity.getName());
        //check email is valid or duplicated
        if(ObjectUtils.isEmpty(client)) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        if(!passwordEncoder.matches(userEntity.getPwd(), client.getPassword())) {
            return RespBody.isFail().msg(ErrorEnum.ORIGINAL_PASSWORD_ERROR);
        }
        // login successfully
        ApiLoginUser loginUser = new ApiLoginUser().setClientId(client.getId())
                .setName(client.getUserName())
                .setEmail(client.getEmail())
                .setPhoneNumber(client.getPhoneNumber())
                .setIsVIP(client.getIsVIP())
                .setClientBasicInfo(new ApiClientBasicEntity(client.getClientBasicInfo()))
                .setExtraInfo(new ApiExtraInfoEntity(client.getExtraInfo()))
                .setWorkExperiences(client.getWorkExperiences().stream().map(ApiWorkExperienceEntity::new).collect(Collectors.toList()))
                .setEducationExperiences(client.getEducationExperiences().stream().map(ApiEducationExperienceEntity::new).collect(Collectors.toList()))
                .setLanguageAbilities(client.getLanguageAbilities().stream().map(ApiLanguageAbilityEntity::new).collect(Collectors.toList()))
                .setSpouseInfo(new ApiSpouseInfoEntity(client.getSpouseInfos().stream().findFirst().get()));
        // set userinfo to context
        String token = Context.addLoginUser(loginUser);

        return RespBody.isOk().data(Map.of("token", token, "user", loginUser));
    }

    public RespBody<?> logout(String token) {
        // set userinfo to context
        Context.removeLoginUser(token);
        return RespBody.isOk();
    }

    /**
     * register a new user
     * @param userEntity to register
     * @return
     */
    public RespBody<?> register(ApiUserEntity userEntity) {
        //check email is valid or duplicated
        if(clientDao.findByEmail(userEntity.getEmail()) != null) {
            return RespBody.isFail().msg(ErrorEnum.EMAIL_EXISTS);
        }

        if(clientDao.findByUserName(userEntity.getName()) != null) {
            return RespBody.isFail().msg(ErrorEnum.NAME_CONFLICT);
        }
        Client user = new Client();
        user.setEmail(userEntity.getEmail());
        user.setUserName(userEntity.getName());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setIsVIP('N');
        //encrypt password
        user.setPassword(passwordEncoder.encode(userEntity.getPwd()));

        user.setLanguage("en-US");
        //save to database
        clientDao.save(user);
        //send verification email
        return RespBody.isOk();
    }

    /**
     * save or update client basic information
     * @param entity  && clientId
     * @return
     */
    public RespBody<?> UpdateClientBasic(ApiClientBasicEntity entity, int clientId ) {
        //first step: set value from entity to obj
        Client client = clientDao.findById(clientId).orElse(null);

        ClientBasicInfo obj = basicInfoDao.findById(entity.getIdClientBasic()).orElse(new ClientBasicInfo());
        obj.setClient(client);
        obj.setAge(entity.getAge());
        obj.setMarriageStatus(entity.getMarriageStatus());
        obj.setImmigrationStatus(entity.getImmigrationStatus());

        //save to database.
        basicInfoDao.save(obj);
        return RespBody.isOk().data(entity.setIdClientBasic(obj.getIdClientBasic()));
    }

    public RespBody<?> UpdateEducationExp(ApiEducationExperienceEntity entity, int clientId ) {
        Client client = clientDao.findById(clientId).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }

        //first step: set value from entity to obj
        EducationExperience obj = educationExperienceDao.findById(entity.getIdEducationExperience()).orElse(new EducationExperience());
        obj.setClient(client);
        obj.setEducationLevel(entity.getEducationLevel());
        obj.setProgram(entity.getProgram());
        obj.setAddress(entity.getAddress());

        //save to database.
        educationExperienceDao.save(obj);
        return RespBody.isOk().data(entity.setIdEducationExperience(obj.getIdEducationExperience()));
    }

    public RespBody<?> UpdateExtraInfo(ApiExtraInfoEntity entity, int clientId ) {
        Client client = clientDao.findById(clientId).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        //first step: set value from entity to obj
        ExtraInfo obj = extraInfoDao.findById(entity.getIdExtraInfo()).orElse(new ExtraInfo());
        obj.setClient(client);
        obj.setRelatives_flg(entity.getRelatives_flg());
        obj.setPNP_flg(entity.getPNP_flg());
        obj.setOffer_flg(entity.getOffer_flg());
        obj.setBilingual_flg(entity.getBilingual_flg());

        //save to database.
        extraInfoDao.save(obj);
        return RespBody.isOk().data(entity.setIdExtraInfo(obj.getIdExtraInfo()));
    }

    public RespBody<?> UpdateLanguageAbility(ApiLanguageAbilityEntity entity, int clientId ) {
        Client client = clientDao.findById(clientId).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        //first step: set value from entity to obj
        LanguageAbility obj = languageAbilityDao.findById(entity.getIdLanguageAbility()).orElse(new LanguageAbility());
        obj.setClient(client);
        obj.setEnglish(entity.getEnglish());
        obj.setFrench(entity.getFrench());
        obj.setObtainedTerm(entity.getObtainedTerm());

        //save to database.
        languageAbilityDao.save(obj);
        return RespBody.isOk().data(entity.setIdLanguageAbility(obj.getIdLanguageAbility()));
    }

    public RespBody<?> UpdateSpouseInfo(ApiSpouseInfoEntity entity, int clientId ) {
        Client client = clientDao.findById(clientId).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        //first step: set value from entity to obj
        SpouseInfo obj = spouseInfoDao.findById(entity.getIdSpouseInfo()).orElse(new SpouseInfo());
        obj.setClient(client);
        obj.setEducationLevel(entity.getEducationLevel());
        obj.setProgram(entity.getProgram());
        obj.setOfficialLanguage(entity.getOfficialLanguage());
        obj.setCodeNOC(entity.getCodeNOC());

        //save to database.
        spouseInfoDao.save(obj);
        return RespBody.isOk().data(entity.setIdSpouseInfo(obj.getIdSpouseInfo()));
    }
    public RespBody<?> UpdateWorkExp(ApiWorkExperienceEntity entity, int clientId ) {
        //first step: set value from entity to obj
        Client client = clientDao.findById(clientId).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        WorkExperience obj = workExperienceDao.findById(entity.getIdWorkExperience()).orElse(new WorkExperience());
        obj.setClient(client);
        obj.setJobTitle(entity.getJobTitle());
        obj.setEnglish(entity.getEnglish());
        obj.setCodeNOC(entity.getCodeNOC());
        obj.setAmtWage(entity.getAmtWage());
        obj.setTxtDuties(entity.getTxtDuties());
        obj.setJobType(entity.getJobType());
        obj.setWorkplaceLocation(entity.getWorkplaceLocation());
        obj.setDateStart(entity.getDateStart());
        //save to database.
        workExperienceDao.save(obj);
        return RespBody.isOk().data(entity.setIdWorkExperience(obj.getIdWorkExperience()));
    }

    public RespBody<?> getDocument(ApiLoginUser apiLoginUser, String type) {
        if (apiLoginUser == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        Client client = clientDao.findById(apiLoginUser.getClientId()).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        List<ClientDocument> list = clientDocumentDao.findListByClientAndTypeOrderByVersionDesc(client, type);
        if (CollectionUtils.isEmpty(list)) {
            return RespBody.isOk().data("");
        }
        ClientDocument document = list.get(0);
        return RespBody.isOk().data(document.getFilePath());
    }

    public RespBody<?> createDocument(ApiLoginUser apiLoginUser, String type) {
        if (apiLoginUser == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        Client client = clientDao.findById(apiLoginUser.getClientId()).orElse(null);
        if (client == null) {
            return RespBody.isFail().msg(ErrorEnum.USER_NOT_FOUND);
        }
        DocumentTypeEnum typeEnum = DocumentTypeEnum.findStatusByValue(type);
        if (!typeEnum.equals(DocumentTypeEnum.STUDY_PLAN)) {
            return RespBody.isFail().msg(ErrorEnum.DATA_ERROR);
        }

        ClientBasicInfo basicInfo = basicInfoDao.findByClient(client);
        if (basicInfo == null) {
            return RespBody.isFail().msg(ErrorEnum.PROFILE_NOT_COMPLETED);
        }
        StringBuilder prompt = new StringBuilder("I am ");
        prompt.append(client.getUserName()).append(". I am ").append(basicInfo.getAge()).append(" years old.");

        List<EducationExperience> educationExperiences = educationExperienceDao.findListByClient(client);
        if (!CollectionUtils.isEmpty(educationExperiences)) {
            for (EducationExperience educationExperience : educationExperiences) {
                prompt.append("My education level is ").append(educationExperience.getEducationLevel()).append(".");
            }
        }

        List<WorkExperience> workExperiences = workExperienceDao.findListByClient(client);
        if (!CollectionUtils.isEmpty(workExperiences)) {
            for (WorkExperience workExperience : workExperiences) {
                prompt.append("My job is ").append(workExperience.getJobTitle()).append(".");
            }
        }

        prompt.append("Now I want to apply for Canadian's Study Visa, " +
                "Please generate a Study Plan for the Visa officer using the information I provided. " +
                "Omit information that I didn't provide. The Longer the better. Thank you.");

        List<String> content = ChatGPTClient.getContent(prompt.toString());
        if (CollectionUtils.isEmpty(content)) {
            return RespBody.isFail().msg(ErrorEnum.DATA_ERROR);
        }
        String fileName = PdfGenerator.generate("Study Plan", content);
        if (StringUtils.isEmpty(fileName)) {
            return RespBody.isFail().msg(ErrorEnum.DATA_ERROR);
        }
        List<ClientDocument> docs = clientDocumentDao.findListByClientAndTypeOrderByVersionDesc(client, type);
        int version = 1;
        if (!CollectionUtils.isEmpty(docs)) {
            version = docs.get(0).getVersion() + 1;
        }
        ClientDocument clientDocument = new ClientDocument();
        clientDocument.setClient(client);
        clientDocument.setType(type);
        clientDocument.setVersion(version);
        clientDocument.setFilePath(fileName);
        clientDocumentDao.save(clientDocument);
        return RespBody.isOk().data(fileName);
    }

    @Transactional
    public RespBody<?> assessment(int programId, ApiLoginUser user) {
        Client client = clientDao.findById(user.getClientId()).orElseThrow(() -> new RuntimeException("user not exist"));
        // get program information
        programDao.findById(programId).ifPresentOrElse(p-> {
            // 创建report表数据
            UserReport report = new UserReport();
            report.setProgram(p);
            report.setProgramName(p.getName());
//            report.setInvitationScore()
            //
            report.setClient(client);
            List<UserReportScore> reportScoreList = new ArrayList<>();

            // 查询score列表
            List<ProgramScore> programScores = programScoreDao.findByProgramId(p.getId());
            if(programScores == null) {
                throw new RuntimeException("Invalid program");
            }
            // 循环
            programScores.forEach(score -> {
                UserReportScore reportScore = new UserReportScore();

                // 根据 score 配置得到对应字段数据
                String targetObjectName = score.getTargetObject();
                String targetFieldName = score.getTargetField();
                String targetMethodName = score.getConditionMethod();

                try {
                    Class<?> targetClass = Class.forName(targetObjectName);
                    // 根据score配置 得到对应字段数据
                    Field[] clientFields = Client.class.getDeclaredFields();
                      for (Field field : clientFields) {
                          // 找到client对象中的该字段
                          // 该字段类型有可能是List集合 或者 targetClass的类型
                          if(field.getType().equals(targetClass)) {
                              field.setAccessible(true);
                              // 从client对象中拿到这个字段对应的值
                              Method clientMethod = Client.class.getDeclaredMethod(ConditionUtils.generateMethodName(field.getName()));
                              clientMethod.setAccessible(true);
                            // 执行get方法得到该对象
                              Object result = clientMethod.invoke(client);
                              setReport( result, targetClass, targetFieldName, targetMethodName, reportScore, score, reportScoreList);
                          }
                          // 处理字段是 List 类型的情况
                          else if (List.class.isAssignableFrom(field.getType())) {
                              ParameterizedType listType = (ParameterizedType) field.getGenericType();
                              Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                              if (listClass.equals(targetClass)) {
                                  Method getListMethod = Client.class.getDeclaredMethod(ConditionUtils.generateMethodName(field.getName()));
                                  getListMethod.setAccessible(true);
                                  List<?> list = (List<?>) getListMethod.invoke(client);

                                  if (list != null) {
                                      for (Object item : list) {
                                          if (item != null) {
//                                              Method targetMethod = targetClass.getDeclaredMethod(ConditionUtils.generateMethodName(targetFieldName));
//                                              targetMethod.setAccessible(true);
//
//                                              Object result = targetMethod.invoke(item);
                                              setReport(item, targetClass, targetFieldName, targetMethodName, reportScore, score, reportScoreList);
                                          }
                                      }
                                  }
                              }
                          }
                      }

                } catch (ClassNotFoundException | NoSuchMethodException |
                         IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Error processing score: " + score, e);
                }

              // 根据score配置 执行对应方法，传入字段
            });
            /*
             * start build prompt
             */
            // dictionary
            List<Map<String, String>> maritalDict = (ArrayList)Context.dictionary("maritalStatus");
            // basic information
            ClientBasicInfo basicInfo = client.getClientBasicInfo();
            StringBuilder prompt = new StringBuilder("I am ");
            if(!ObjectUtils.isEmpty(basicInfo)) {
                Optional.ofNullable(basicInfo.getAge()).ifPresent(age -> {
                    prompt.append(age + " Years old, ");
                });
                Optional.ofNullable(basicInfo.getMarriageStatus()).ifPresent(status -> {
                    prompt.append("i'm " + maritalDict.stream().filter(m->m.get("value").equals(status)).findFirst().get().get("name") + ", ");
                });
            }
            prompt.append("Now I want to apply for Canada's " + report.getProgramName() + ", " +
                    "Please generate a give me a suggestion to improve my score. " +
                    " The Longer the better. Thank you.");

            List<String> content = ChatGPTClient.getContent(prompt.toString());
            /* end */
            // ai评估建议
            report.setContent(content.toString());
            // calculate score
            report.setScore(reportScoreList.stream().mapToInt(UserReportScore::getScore).sum());
            // 保存report和report得分项目
            userReportDao.save(report);
            // 拿到report的id，保存reportscore
            reportScoreList.forEach(s->{
                s.setReport(report);
                userReportScoreDao.save(s);
            });

        },()->{
            throw new RuntimeException("Invalid program");
        });
        //
        return RespBody.isOk();
    }

    private void setReport(Object result, Class<?> targetClass, String targetFieldName, String targetMethodName, UserReportScore reportScore, ProgramScore score, List<UserReportScore> reportScoreList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        // 根据该对象中targetFieldName 实体名 得到值
        Method targetMethod = targetClass.getDeclaredMethod(ConditionUtils.generateMethodName(targetFieldName));
        targetMethod.setAccessible(true);

        // 执行对应方法，传入字段
        Object targetResult = targetMethod.invoke(result);
        // 得到值后， 通过加载 ConditionUtils中的targetMethodName方法得到设置好的score
        Method conditionMethod = Arrays.stream(ConditionUtils.class.getDeclaredMethods()).filter(method->method.getName().equals(targetMethodName)).findFirst().get();
//                              Method conditionMethod = ConditionUtils.class.getDeclaredMethod(targetMethodName);
        Parameter[] parameters = conditionMethod.getParameters();

        Object[] params = new Object[parameters.length];
        //第一个参数永远是当前值
        buildParams(params, 0, parameters[0].getType(), targetResult);
        for (int i = 0; i < parameters.length-1; i++) {
            Method m = score.getClass().getDeclaredMethod("getConditionValue" +(i+1));
            m.setAccessible(true);
            Object a = m.invoke(score);;
            // 转换 a 为 parameters[i].getType() 的类型
            Class<?> paramType = parameters[i].getType();
            buildParams(params, i+1, paramType, a);
        }
        conditionMethod.setAccessible(true);
        // is reach the requirement
        boolean isReach = (boolean) conditionMethod.invoke(null, params);
        // 将 result 保存到 report 的某个属性
        if (isReach) {
            // 给report score添加记录
            reportScore.setTargetObject(score.getTargetObject());
            reportScore.setTargetField(score.getTargetField());
            reportScore.setScore(score.getScore());
            reportScore.setContent(score.getContent());
            reportScoreList.add(reportScore);
        }
    }

    private void buildParams(Object[] params, int index, Class<?> paramType, Object a) {

        if (paramType.isInstance(a)) {
            params[index] = paramType.cast(a);
        } else {
            // 如果 paramType 是基本类型，需要进行特殊处理
            if (paramType == int.class) {
                params[index] = Integer.parseInt(a.toString());
            } else if (paramType == long.class) {
                params[index] = Long.parseLong(a.toString());
            } else if (paramType == double.class) {
                params[index] = Double.parseDouble(a.toString());
            } else if (paramType == float.class) {
                params[index] = Float.parseFloat(a.toString());
            } else if (paramType == boolean.class) {
                params[index] = Boolean.parseBoolean(a.toString());
            } else if (paramType == char.class) {
                params[index] = a.toString().charAt(0);
            } else if (paramType == byte.class) {
                params[index] = Byte.parseByte(a.toString());
            } else if (paramType == short.class) {
                params[index] = Short.parseShort(a.toString());
            } else {
                // 处理其他类型的转换
                params[index] = paramType.cast(a);
            }
        }
    }
    public RespBody<?> deleteLanguageAbility(int id, int clientId) {
        // find language ability from id
         languageAbilityDao.findById(id).ifPresent(lnaguage -> {
            if(lnaguage.getClient().getId() == clientId) {
                languageAbilityDao.deleteById(id);
            }else {
                throw new InvalidParameterException("No record exists for language ability");
            }
        });
        return RespBody.isOk();
    }

    public RespBody<?> EducationExp(int id, int clientId) {
        // find language education from id
        educationExperienceDao.findById(id).ifPresent(education -> {
            if(education.getClient().getId() == clientId) {
                educationExperienceDao.deleteById(id);
            }else {
                throw new InvalidParameterException("No record exists for education experience");
            }
        });
        return RespBody.isOk();
    }

    public RespBody<?> deleteWorkExp(int id, int clientId) {
        // find work exp from id
        workExperienceDao.findById(id).ifPresent(work -> {
            if(work.getClient().getId() == clientId) {
                workExperienceDao.deleteById(id);
            }else {
                throw new InvalidParameterException("No record exists for education experience");
            }
        });
        return RespBody.isOk();
    }

    /**
     * get report information by report id
     * @param reportId
     * @return
     */
    public RespBody<?> findReportById(int reportId) {
        // get report information
        ApiMyReport apiMyReport = userReportDao.findById(reportId).map(report -> {
            ApiMyReport apiReport = new ApiMyReport();
            apiReport.setId(report.getId());
            apiReport.setLastInviteScore(report.getProgram().getInvitations().stream().sorted(Comparator.comparing(ProgramInvitation::getInvitedDate)).collect(Collectors.toList()).stream().findFirst().orElse(new ProgramInvitation()).getMinimumScore());
            apiReport.setScore(report.getScore());
            apiReport.setProgramName(report.getProgramName());
            apiReport.setContent(report.getContent());
                // get report score by report id
            userReportScoreDao.findByReportId(reportId).forEach(score -> {
                apiReport.getReportScores().add(new ApiReportScore().setScore(score.getScore())
                        .setTargetObject(score.getTargetObject())
                        .setTargetField(score.getTargetField())
                        .setId(score.getId())
                        .setContent(score.getContent()));
            });
        return apiReport;
        }).get();

        return RespBody.isOk().data(apiMyReport);
    }
}
