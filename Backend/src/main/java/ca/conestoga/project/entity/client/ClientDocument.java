package ca.conestoga.project.entity.client;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for client document
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "client_document")
public class ClientDocument {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /*
   * user id
   */
  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "document_type", nullable = false)
  private String type;

  @Column(nullable = false)
  private int version;

  @Column(nullable = false)
  private String filePath;
}
