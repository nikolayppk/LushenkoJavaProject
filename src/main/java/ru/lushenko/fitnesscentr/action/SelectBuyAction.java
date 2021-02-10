package ru.lushenko.fitnesscentr.action;

import ru.lushenko.fitnesscentr.console.Action;
import ru.lushenko.fitnesscentr.console.ConsoleDialog;
import ru.lushenko.fitnesscentr.domain.Buy;
import ru.lushenko.fitnesscentr.domain.Repository;
import ru.lushenko.fitnesscentr.domain.TypeSubscription;

import java.util.Random;

public class SelectBuyAction implements Action {

    private String title;
    private String question;
    private Repository<String, TypeSubscription> repository;
    private Repository<String, Buy> buyRepository;

    public SelectBuyAction(String title, String question, Repository<String, TypeSubscription> repository, Repository<String, Buy> buyRepository) {
        this.title = title;
        this.question = question;
        this.repository = repository;
        this.buyRepository = buyRepository;
    }

    @Override
    public void run() {
        printSubscription();
        System.out.println(repository.getAll().size() + 1 + " - Назад");
        buySubscription();
        System.out.println("*********************************");
    }

    @Override
    public String title() {
        return title;
    }

    /***
     * Метод для отображения абонементов
     */
    private void printSubscription() {
        for (TypeSubscription typeSubscription : this.repository.getAll()) {
            System.out.println(typeSubscription.getId() + " - " + typeSubscription.getName());
        }
    }

    /***
     * Метод для выполнения покупки
     */
    private void buySubscription() {
        //Вводим позицию абонемента, который хотим купить
        boolean doneBuy = false;
        while (doneBuy == false) {
            String position = new ConsoleDialog().ask(question);
            doneBuy = false;
            for (TypeSubscription typeSubscription : repository.getAll()) {
                int kol = repository.getAll().size() + 1;
                String numberForExit = "" + kol;
                if (position.equals(numberForExit)) {
                    doneBuy = true;
                    break;
                } else if (typeSubscription.getId().equals(position)) {
                    Buy buy = new Buy(typeSubscription.getName(), generationRandomId(5));
                    /*Выполняем запись покупки*/
                    buyRepository.add(buy);
                    /*Отображение ID покупки*/
                    System.out.println("Вы выбрали абонемент " + typeSubscription.getName() + ", ID покупки: " + buy.getId());
                    doneBuy = true;
                    break;
                }
            }
            if (doneBuy == false)
                System.out.println("Введенное значение отсутствует в списке. Введите корректное значение.");
        }
    }

    /*Генерируем ID покупки*/
    private String generationRandomId(int length) {
        String mCHAR = "0123456789";
        int strLength = length;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builder.append(ch);
        }
        return builder.toString();
    }

}
