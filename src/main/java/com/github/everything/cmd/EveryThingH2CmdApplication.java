package com.github.everything.cmd;

import com.github.everything.core.EverythingManager;
import com.github.everything.core.model.Condition;

import java.util.Scanner;

public class EveryThingH2CmdApplication {
    public static void main(String[] args) {

        EverythingManager manager = EverythingManager.getInstance();

        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用，everything-h2");
        manager.help();
        while (true){
            System.out.print(">>");
            String line = scanner.nextLine();
            switch (line){
                case "help":
                    manager.help();
                    break;
                case "quit":
                    manager.quit();
                    break;
                case "index":
                    manager.buildIndex();
                    break;
                default:{
                   if(line.startsWith("search")){
                       String[] segments = line.split(" ");
                       if (segments.length >= 2){
                           Condition condition = new Condition();
                           String name = segments[1];
                           condition.setName(name);
                           if (segments.length >= 3){
                               String type = segments[2];
                               condition.setFileType(type.toUpperCase());
                           }
                           manager.search(condition).forEach(thing -> {
                               System.out.println(thing.getPath());
                           });
                       } else {
                           manager.help();
                       }
                   } else {
                       manager.help();
                   }
                }
            }
        }
    }
}
