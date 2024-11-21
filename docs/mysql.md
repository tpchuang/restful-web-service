To use MySQL DB instead of H2.

1. Start MySQL server:
   ```bash
   # using user=wheel, password=dummy, database=sns-database as example
   docker run --detach \
   --env MYSQL_ROOT_PASSWORD=top-secret \
   --env MYSQL_USER=wheel \
   --env MYSQL_PASSWORD=dummy \
   --env MYSQL_DATABASE=sns-database \
   --name mysql \
   --publish 3306:3306 \
   mysql:8-oracle
   ```

2. Add MySQL dependency to `pom.xml`:
   replace
   ```xml 
   <dependency>
     <groupId>com.h2database</groupId>
     <artifactId>h2</artifactId>
     <version>2.2.224</version>
     <scope>runtime</scope>
   </dependency>
   ```
   with
   ```xml
   <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>8.0.33</version>
   </dependency>
   ```

3. Update `application.properties`:
   replace
   ```properties
   spring.datasource.url=jdbc:h2:mem:test-db
   ```
   with
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sns-database
   spring.datasource.username=wheel
   spring.datasource.password=dummy
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```
