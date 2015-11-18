package org.vmykhalchuk.where2go4lunch;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.vmykhalchuk.where2go4lunch.rest.InitEndpoint;
import org.vmykhalchuk.where2go4lunch.rest.RestaurantEndpoint;
import org.vmykhalchuk.where2go4lunch.rest.UserAdminEndpoint;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
    	packages("org.vmykhalchuk.where2go4lunch.rest");
    	register(InitEndpoint.class);
        register(RestaurantEndpoint.class);
        register(UserAdminEndpoint.class);
    }
}
