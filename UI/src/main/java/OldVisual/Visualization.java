package OldVisual;

import DataBase.DataBaseOLD;
import PojoClass.User;
import Signal.Signal;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Visualization {
    private DataBaseOLD dataBaseOLD;
    private Scanner scanner;
    private User user;
    private Signal signal;

    public Visualization(DataBaseOLD dataBaseOLD) {
        this.dataBaseOLD = dataBaseOLD;
        this.scanner = new Scanner(System.in);
    }

    public DataBaseOLD getDataBaseOLD() {
        return dataBaseOLD;
    }

    public void setDataBaseOLD(DataBaseOLD dataBaseOLD) {
        this.dataBaseOLD = dataBaseOLD;
    }

    public void mainMenu() throws SQLException, IOException {
        System.out.println("Добро пожаловать в Планировщик задач");
        System.out.print("Введите имя пользователя: ");
        System.out.println();
        System.out.println("Для выхода введите 1");
        String name = scanner.next();
        if(name.equals("1")){
            System.exit(0);
        }else{
            if(dataBaseOLD.getEqualsUserName(name)){
           //     user = new User(dataBaseOLD.getIdUserInName(name),name, dataBaseOLD);
                clearConsole();
                System.out.println("Вход в систему выполнен");
          //      signal = new Signal(dataBaseOLD,user);
                signal.startSignal();//Запуск уведомлений сразу, как пользователь авторизовался
                menuTaskUser();
            }else{
                clearConsole();
                System.out.println("Такого пользователя не существует!");
                mainMenu();
            }
        }
    }

    //меню пользователя
    private void menuTaskUser() throws SQLException, IOException {
        dataBaseOLD.printTasksUser(user.getIdUser());
        System.out.println();
        System.out.println("Чтобы добавить задачу, нажмите 1");
        System.out.println("Чтобы удалить задачу, нажмите 2");
        System.out.println("Чтобы редактировать задачу, нажмите 3");
        System.out.println("Чтобы вернуться назад, нажмите 4");
        System.out.println("Чтобы выйти, нажмите 0");
        int button = scanner.nextInt();
        switch(button){
            case 0: //выход
                System.exit(0);
                break;
            case 1: //добавление задачи
                clearConsole();
                addTask();
                break;
            case 2: //удаление задачи
                clearConsole();
                dellTask();
                break;
            case 3: //редактирование задачи
                clearConsole();
                setTask();
                break;
            case 4: //назад
                user = null;
                clearConsole();
                mainMenu();
                break;
        }
    }

    //Добаление задачи
    private void addTask() throws SQLException, IOException {
        System.out.println("Введите название задачи");
        String nameTask = scanner.next();
        System.out.println("Введите год исполнения задачи"); //Добавить проверку на те года, которые уже прошли
        int year = scanner.nextInt();
        System.out.println("Введите месяц исполнения задачи");
        int mounth = scanner.nextInt();
        System.out.println("Введите день исполнения задачи");
        int day = scanner.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);

        System.out.println("Введите час исполнения задачи"); //Добавить проверку на то время, которое уже прошло
        int hour = scanner.nextInt();
        System.out.println("Введите минуты исполнения задачи");
        int minute = scanner.nextInt();

        calendar.set((year-1),(mounth-1),day,hour,minute,00);

        long dateCalendar = calendar.getTimeInMillis();
        java.sql.Date dateSqlCalendar = new java.sql.Date(dateCalendar);
        java.sql.Time timeSqlCalendar = new java.sql.Time(dateCalendar);

        System.out.println("Введите описание задачи: ");
        String descriptionTask = scanner.next();

        dataBaseOLD.addTask(user.getIdUser(),nameTask,descriptionTask, dateSqlCalendar, timeSqlCalendar);
        System.out.println("Задача добавлена.");
        clearConsole();
        menuTaskUser();
    }

    //Удаление задачи
    private void dellTask() throws SQLException, IOException {
        dataBaseOLD.printTasksUser(user.getIdUser());
        System.out.println("Введите номер задачи, которую вы хотите удалить: ");
        int idTask = scanner.nextInt();
        if(dataBaseOLD.getEqualdTask(idTask)){
            dataBaseOLD.deleteTask(idTask);
            System.out.println("Задача удалена");
            clearConsole();
            menuTaskUser();
        }else{
            System.out.println("Такой задачи нет");
            clearConsole();
            dellTask();
        }
    }

    //Изменение задачи
    private void setTask() throws SQLException, IOException {
        dataBaseOLD.printTasksUser(user.getIdUser());
        System.out.println("Введите номер задачи, которую вы хотите изменить: ");
        int idTask = scanner.nextInt();
        if (dataBaseOLD.getEqualdTask(idTask)) {
            System.out.println("Введите название задачи");
            String nameTask = scanner.next();
            System.out.println("Введите год исполнения задачи"); //Добавить проверку на те года, которые уже прошли
            int year = scanner.nextInt();
            System.out.println("Введите месяц исполнения задачи");
            int mounth = scanner.nextInt();
            System.out.println("Введите день исполнения задачи");
            int day = scanner.nextInt();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);

            System.out.println("Введите час исполнения задачи"); //Добавить проверку на то время, которое уже прошло
            int hour = scanner.nextInt();
            System.out.println("Введите минуты исполнения задачи");
            int minute = scanner.nextInt();

            calendar.set((year-1),(mounth-1),day,hour,minute,00);

            Date date = calendar.getTime();
            Time time = new Time(date.getTime());

            System.out.println("Введите описание задачи: ");
            String descriptionTask = scanner.next();

            dataBaseOLD.addTask(user.getIdUser(),nameTask,descriptionTask, date, time);
            System.out.println("Задача изменена");
            clearConsole();
            menuTaskUser();
        } else {
            System.out.println("Такой задачи нет");
            clearConsole();
            setTask();
        }
    }

    //Ощищение консоли
    private void clearConsole() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
