import psycopg2
from psycopg2 import sql

# Database configuration
DB_NAME = "tournamentdb"
DB_USER = "postgres"
DB_PASSWORD = "123"
DB_HOST = "localhost"
DB_PORT = "5432"

def reset_database():
    try:
        conn = psycopg2.connect(
            dbname="postgres",
            user=DB_USER,
            password=DB_PASSWORD,
            host=DB_HOST,
            port=DB_PORT
        )
        conn.autocommit = True

        cursor = conn.cursor()

        cursor.execute(sql.SQL(
            "SELECT pg_terminate_backend(pg_stat_activity.pid) "
            "FROM pg_stat_activity "
            "WHERE pg_stat_activity.datname = %s AND pid <> pg_backend_pid();"
        ), [DB_NAME])

        cursor.execute(sql.SQL("DROP DATABASE IF EXISTS {}").format(sql.Identifier(DB_NAME)))
        cursor.execute(sql.SQL("CREATE DATABASE {}").format(sql.Identifier(DB_NAME)))

        print(f"Database {DB_NAME} has been reset.")

    except Exception as e:
        print(f"An error occurred: {e}")

    finally:
        # Close the cursor and connection
        cursor.close()
        conn.close()

if __name__ == "__main__":
    reset_database()