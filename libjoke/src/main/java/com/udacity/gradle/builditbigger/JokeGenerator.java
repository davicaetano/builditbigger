package com.udacity.gradle.builditbigger;

import java.util.Random;

public class JokeGenerator
{
    public String JokeGen(){
        Random random = new Random();
        String[] jokes = {"Software is like sex: It’s better when it’s free.",
                "Q: Why did the ants dance on the jam jar?\nA: The lid said, \"Twist to open.\"",
                "Q: how many programmers does it take to change a light bulb?\nA: none, that’s a hardware problem"};
        return jokes[random.nextInt(jokes.length)];
    }
}
