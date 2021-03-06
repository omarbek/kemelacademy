package kz.academy.kemelacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-24
 * @project kemelacademy
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Autowired
    private ServletContext servletContext;
    
    Contact contact = new Contact(
            "Omarbek Dinassil",
            "https://www.facebook.com/omarbek.dinassil",
            "dinasil.omarbek@gmail.com"
    );
    
    List<VendorExtension> vendorExtensions = new ArrayList<>();
    
    ApiInfo apiInfo = new ApiInfo(
            "Kemel Academy App RESTful Web Service documentation",
            "This pages documents Kemel Academy app RESTful Web Service endpoints",
            "1.0",
            "https://kemelacademy.kz/",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions
    );
    
    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host("api.uirenu.online")
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return null;
                    }
                })
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kz.academy.kemelacademy"))
                .paths(PathSelectors.any())
                .build();
        
        return docket;
    }
    
    @Bean
    public LinkDiscoverers discovers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
    
}
