/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.davi.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.builditbigger.JokeGenerator;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.davi.example.com",
                ownerName = "backend.myapplication.davi.example.com",
                packagePath = ""
        )
)
public class MyEndpoint
{

    @ApiMethod(name = "joke")
    //public MyBean sayHi(@Named("name") String name)
    public MyBean joke()
    {
        MyBean response = new MyBean();
        JokeGenerator jokeGenerator = new JokeGenerator();
        response.setData(jokeGenerator.JokeGen());
        return response;
    }
}