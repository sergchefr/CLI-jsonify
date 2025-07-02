package ru.ifmo;

import java.util.*;

public class ConsoleIO {
    private Scanner console;
    private IcommandManager commandManager;

    private static ConsoleIO instance;

    private ConsoleIO() {
        console = new Scanner(System.in);
    }

    public void start(){
        System.out.println("program started. Type commands or \"help\" for help");
        while (true) {

            String ans = commandManager.getAnswers();
            if(!ans.isEmpty()) System.out.println(ans);

            //ввод команды
            String command = "";
            System.out.print(">>> ");
            try {
                command = console.nextLine();
            } catch (NoSuchElementException e) {
                shutdown();
            }

            //Проверка, какой режим нужен. Запускать ли конструктор?
            command = deleteExtraSpace(command);
            String[] splittedCommand = (command.split(" "));
            if(splittedCommand.length==0) continue;
            if(ArrayHasFlag(splittedCommand,"-c")){
                //в команде указан флаг
                String comName = splittedCommand[0];
                var verifierCommand = commandManager.getVerifierCommand(comName);
                if(verifierCommand==null) {
                    System.out.println("команды с таким именем не существует");
                    continue;
                }
                if(verifierCommand.haveFlag("-c")){
                    //доконструирование и отправка

                    command = constructor(command);
                    //System.out.println("выполнение: "+ command);
                    commandManager.execute(command);
                }else System.out.println("команда не поддерживает интерактивный конструктор");
            }else{
                //в команде не указан флаг
                var t2 = commandManager.getVerifierCommand(splittedCommand[0]);
                if(t2==null) {
                    System.out.println("команды с таким именем не существует");
                    continue;
                }
                //немой ввод
                if (splittedCommand.length == 2 & t2.getParameters().length == 1) {
                    if (!splittedCommand[1].contains("=")) {
                        command = splittedCommand[0] + " " + t2.getParameters()[0].getName() + "=" + splittedCommand[1];
                    }
                }
                if(t2.verify(command)){

                    //System.out.println("выполнение: "+ command);
                    commandManager.execute(command);
                }
                else System.out.println("команда введена неверно");
            }
        }
    }

    public String constructor(String prevcom){
        //prevcom = deleteExtraSpace(prevcom.replace(" -c ", " "));
        String[] args =Arrays.copyOfRange(prevcom.split(" "),1,prevcom.split(" ").length-1);

        VerifierCommand com = commandManager.getVerifierCommand(prevcom.split(" ")[0]);
        Parameter[] parameters = com.getParameters();

        //проверка на немой ввод
        if(parameters.length==1&args.length==1){
            if(parameters[0].verify(args[0])){
                if(args[0].contains("="))return com.getName()+" "+args[0];
                else return com.getName()+" "+parameters[0].getName()+"="+args[0];
            }
        }

        //основная часть

        //если есть аргументы без "=" - выкидываем.
        LinkedList<String> argLinkedList = new LinkedList<>();
        for (String t1: args) {
            if(t1.contains("=")) argLinkedList.add(t1);
        }

        //парсим аргументы в списке. если они норм, добавляем в билдер пытаемся добавить в билдер.
        CommandBuilder builder = new CommandBuilder(com);
        for (String s : argLinkedList) {
            builder.addParameter(s);
        }

        //через запрос билдера вводим недостающие аргументы в билдер. если все элементы добавлены, билдим команду. возвращаем ее
        Parameter nextParam;
        String input;
        while (!builder.isReady()){
            nextParam = builder.nextParameter();
            System.out.println(nextParam.getName()+":"+nextParam.getLimitations());
            try {
                System.out.print(nextParam.getName()+"=");
                input = console.nextLine();
                builder.addParameter(nextParam.getName()+"="+input.strip());
            } catch (NoSuchElementException e) {
                shutdown();
            }catch (RuntimeException e){
                continue;
            }

        }
        return builder.build();

    }

    public void print(String str){
        System.out.println(str);
    }

    private void shutdown(){
        System.out.println("emergency exit");
        //manager.exit(1);
        try {
            Thread.sleep(2000);
            System.exit(1);
        } catch (InterruptedException ex) {
            System.exit(2);
        }
    }

    private String deleteExtraSpace(String a) {
       while (a.contains("  ")|a.contains("= ")|a.contains(" =")){
           a=a.replace("  "," ");
           a=a.replace("= ","=");
           a=a.replace(" =","=");
       }
       return a.strip();
    }

    private boolean ArrayHasFlag(String[] args,String flag){
        for (String s : args) {
            if(s.equals(flag)) return true;
        }
        return false;
    }

    public void setCommandManager(CommandManagerImpl CommandManager) {
        this.commandManager = CommandManager;
    }

    public static ConsoleIO getInstance(){
        if(instance==null){
            instance=new ConsoleIO();
        }
        return instance;
}
}
