package hiram.swagger;

import hiram.constant.Constants;
import hiram.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/12 18:33
 * @Description: "swagger配置类"
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private TokenProperties tokenProperties;

    @Bean
    public Docket allApis(){
        //添加header参数
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder
                .name(Constants.TOKEN_HEADER).description("user token")
                .modelRef(new ModelRef("String")).parameterType("header")
                //header中的token参数非必填，传空也可以
                .required(false)
                .build()
        ;
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.isEnabled())
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hiram"))
                .build()
                .groupName("all")
                .globalOperationParameters(parameters)
        ;
    }


    private ApiInfo buildApiInfo(){

        String title = "vein_scan_dms_api";
        String description = "description";
        String version = "v0.1";
        String termsOfServiceUrl = "";
        Contact contact = new Contact("HiramHe", "", "hiram.he@qq.com");
        String license = "Apache 2.0";
        String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";
        List< VendorExtension > vendorExtensions = new ArrayList<>();

        return new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
                contact,
                license,
                licenseUrl,
                vendorExtensions
        );

    }
}
