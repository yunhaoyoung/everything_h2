package com.github.everything.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class EverythingConfig {
    private static volatile EverythingConfig config;
    @Getter
    private HandlerPath handlerPath = HandlerPath.getDefaultHandlerPath();

    @Getter
    @Setter
    private Integer maxReturn = 30;

    @Getter
    @Setter
    private Boolean enableBuildIndex = false;

    @Getter
    @Setter
    private Boolean orderByDesc = false;


    private EverythingConfig(){

    }
    public static EverythingConfig getInstance(){
        if(config == null){
            synchronized (EverythingConfig.class){
                if (config == null){
                    config = new EverythingConfig();
                }
            }
        }
        return config;
    }


}
