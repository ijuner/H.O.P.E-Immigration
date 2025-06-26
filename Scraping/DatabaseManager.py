import pymysql

class DatabaseManager:
    def __init__(self, host, user, password, database):
        """init"""
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def insert_data(self, table, data):
        """insert data"""
        keys = ', '.join(data.keys())
        values = ', '.join(['%s'] * len(data))
        sql = f"INSERT INTO `{table}` ({keys}) VALUES ({values})"
        with self.connection.cursor() as cursor:
            cursor.execute(sql, tuple(data.values()))
        self.connection.commit()

    def update_data(self, table, changes, condition):
        """update data"""
        update_stmt = ', '.join([f"{key} = %s" for key in changes.keys()])
        sql = f"UPDATE `{table}` SET {update_stmt} WHERE {condition}"
        with self.connection.cursor() as cursor:
            cursor.execute(sql, tuple(changes.values()))
        self.connection.commit()

    def select_data(self, table, columns, condition=None):
        """select data"""
        columns_formatted = ', '.join(columns)
        sql = f"SELECT {columns_formatted} FROM `{table}`"
        if condition:
            sql += f" WHERE {condition}"
        with self.connection.cursor() as cursor:
            cursor.execute(sql)
            return cursor.fetchall()

    def close(self):
        """close database connection"""
        self.connection.close()
