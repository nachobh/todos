package com.todos.prueba.main;

import com.todos.prueba.controller.MainController;
import com.todos.prueba.entity.ToDo;
import com.todos.prueba.repository.H2ToDoRepository;
import com.todos.prueba.repository.impl.UrlToDoRepositoryImpl;
import com.todos.prueba.service.impl.ToDoServiceImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableCaching
@EnableJpaRepositories(basePackageClasses = {H2ToDoRepository.class})
@EnableScheduling
@EnableSwagger2
@EnableTransactionManagement
@EntityScan(basePackageClasses = {ToDo.class})
@Import({MainController.class,
        ToDoServiceImpl.class,
        UrlToDoRepositoryImpl.class})
@SpringBootConfiguration
public class AppConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.todos.prueba.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
}

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ToDo REST API",
                "Test made for the interview process with xxxxxx.",
                "API TOS",
                "https://www.termsfeed.com/public/uploads/2019/04/terms-of-service-template.pdf",
                new Contact("Ignacio Benito Herrero", "https://www.linkedin.com/nachobh",
                        "ibenitoherrero@gmail.com.com"),
                "License of API", "https://www.gnu.org/licenses/license-list.es.html",
                Collections.emptyList());
    }

}
