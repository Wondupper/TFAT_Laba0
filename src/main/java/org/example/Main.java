package org.example;

import org.example.automatons.Automaton;
import org.example.automatons.DeterministicAutomaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;
import org.example.converters.Converter;
import org.example.converters.EpsNonDetToNonDetConverter;
import org.example.converters.NonDetToDetConverter;
import org.example.io.AutomatonToJson;
import org.example.io.JsonToAutomaton;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Введите путь к автомату");
            String path = System.console().readLine();
            Automaton automaton = JsonToAutomaton.getAutomaton(path);
            showTable(automaton);
            System.out.println("Введите последовательность");
            System.out.println("Разрешенные символы: " + automaton.getAlphabet());
            List<String> input = Arrays.stream(System.console().readLine().split("")).toList();
            System.out.println("Результат работы: " + automaton.runAutomaton(input));
            if (!JsonToAutomaton.type.equals("DKA")) {
                System.out.println("Введите путь для сохранения преобразованного автомата");
                String path2 = System.console().readLine();
                switch (JsonToAutomaton.type) {
                    case "NKA" -> {
                        Converter<NonDeterministicAutomaton, DeterministicAutomaton> converter = new NonDetToDetConverter((NonDeterministicAutomaton) automaton);
                        AutomatonToJson.saveAutomatonToJson(path2, converter.convert());
                    }
                    case "ENKA" -> {
                        Converter<EpsNonDeterministicAutomaton, NonDeterministicAutomaton> converter = new EpsNonDetToNonDetConverter((EpsNonDeterministicAutomaton) automaton);
                        AutomatonToJson.saveAutomatonToJson(path2, converter.convert());
                    }
                }
            }
            System.out.println("Для работы с новым автоматом введите 'y'");
            if (!System.console().readLine().equals("y")) break;
        }
    }

    public static void showTable(Automaton automaton) {
        int maxStateColumn = findMaxStateColumnSize(automaton);
        int maxAlphabetColumn = findMaxAlphabetColumnSize(automaton);
        maxAlphabetColumn++;
        maxStateColumn += 3;
        List<String> alphabetList = automaton.getAlphabet().stream().toList();
        System.out.print(" ".repeat(maxStateColumn));
        for (String symbol : alphabetList) {
            System.out.print("|" + " ".repeat(maxAlphabetColumn / 2) + symbol + " ".repeat(maxAlphabetColumn / 2));
        }
        if (JsonToAutomaton.type.equals("ENKA")) {
            System.out.print("|" + " ".repeat(maxAlphabetColumn / 2) + "e" + " ".repeat(maxAlphabetColumn / 2));
        }
        System.out.println();
        for (String state : automaton.getTransitionTable().keySet()) {
            int stateColumn = maxStateColumn;
            if (automaton.getFinalStates().contains(state)) {
                System.out.print("*");
                stateColumn--;
            }
            ;
            if (automaton.getStartState().equals(state)) {
                System.out.print("->");
                stateColumn-=2;
            }
            System.out.print(state + " ".repeat(stateColumn - state.length()));
            for (String symbol : alphabetList) {
                if(automaton.getTransitionTable().get(state).containsKey(symbol)) {
                    String str = automaton.getTransitionTable().get(state).get(symbol).toString();
                    System.out.print("|" + str + " ".repeat(maxAlphabetColumn - str.length()));
                }else{
                    System.out.print("|" +" ".repeat(maxAlphabetColumn));
                }
            }
            if (JsonToAutomaton.type.equals("ENKA")) {
                if(automaton.getTransitionTable().get(state).containsKey("e")) {
                    String str = automaton.getTransitionTable().get(state).get("e").toString();
                    System.out.print("|" + str + " ".repeat(maxAlphabetColumn - str.length()));
                }else{
                    System.out.print("|"+" ".repeat(maxAlphabetColumn));
                }
            }
            System.out.println();
        }
    }

    public static int findMaxStateColumnSize(Automaton automaton) {
        return Collections.max(automaton.getStates(), Comparator.comparing(String::length)).length();
    }

    public static int findMaxAlphabetColumnSize(Automaton automaton) {
        int maxLength = 0;
        for (String state : automaton.getTransitionTable().keySet()) {
            for (String symbol : automaton.getTransitionTable().get(state).keySet()) {
                maxLength = Math.max(maxLength, automaton.getTransitionTable().get(state).get(symbol).toString().length());
            }
        }
        return maxLength;
    }
}