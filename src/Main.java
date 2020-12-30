import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int size = ask_size_to_play(); // определяем размер поля
        char[][] array_to_play = new char[size][size]; // задаем массив для поля игры
        char array_symbol = get_array_symbol(); // определяем тип пустого поля
        make_array(array_to_play, array_symbol); // создаем поле для игры

        do {
            get_player_move(array_to_play, size);
            if (check_player(array_to_play, size)) {
                break;
            }
            get_computer_move(array_to_play, size);
            if (check_computer(array_to_play, size)) {
                break;
            }
        } while (true);
    }

    /** Выбор размера поля для игры */
    static int ask_size_to_play() {
        int size_to_play;
        int game_field;
        do {
            Scanner size = new Scanner(System.in);
            System.out.printf("Введите размер поля, на котором хотите играть в крестики-нолики:%n0 - 3х3%n1 - 5х5%n");
            size_to_play = size.nextInt();
        } while (size_to_play != 0 && size_to_play != 1);
        if (size_to_play == 0) {
            game_field = 3;
            System.out.println("Отлично! Играем на поле 3х3!");
        } else {
            game_field = 5;
            System.out.println("Отлично! Играем на поле 5х5!");
        }
        return game_field;
    }

    /** Определение символа для пустой ячейки поля */
    static char get_array_symbol() {
        char symbol = '-';
        return symbol;
    }

    /** Создаем первоначальное поле для игры */
    static void make_array(char[][] array_to_play, char array_symbol) {
        for (int i = 0; i < array_to_play.length; i++) {
            for (int j = 0; j < array_to_play.length; j++) {
                array_to_play[i][j] = array_symbol;
            }
        }
    }

    /** Запрос координат */
    static int get_coordinate(int size, char coordinate_type) {
        int coordinate;
        do {
            System.out.println("Введи координату " + coordinate_type + " от 1 до " + size);
            Scanner x_y = new Scanner(System.in);
            coordinate = x_y.nextInt();
        } while (coordinate < 1 || coordinate > size);
        return coordinate;
    }

    /** Ход игрока */
    static void get_player_move(char[][] array_to_play, int size) {
        System.out.println("Твой ход!");
        int coordinate_x, coordinate_y;
        do {
            coordinate_x = get_coordinate(size, 'X') - 1;
            coordinate_y = get_coordinate(size, 'Y') - 1;
        } while (test_move(array_to_play, coordinate_x, coordinate_y));
        array_to_play[coordinate_x][coordinate_y] = 'X';
        print_array(array_to_play);
    }

    /** Ход компьютера */
    static void get_computer_move(char[][] array_to_play, int size) {
        System.out.println("Ход компьютера!");
        int coordinate_x, coordinate_y;
        do {
            coordinate_x = (int) (Math.random() * size);
            coordinate_y = (int) (Math.random() * size);
        } while (test_move(array_to_play, coordinate_x, coordinate_y));
        array_to_play[coordinate_x][coordinate_y] = '0';
        print_array(array_to_play);
    }

    /** Проверка поля на незанятость */
    static boolean test_move(char[][] array_to_play, int coordinate_x, int coordinate_y) {
        return (array_to_play[coordinate_x][coordinate_y] != get_array_symbol());
    }

    /** Проверка вертикалей */
    static boolean check_vertical(char[][] array_to_play, int size, char XO, int i) {
        int test = 0;
        for (int a = 0; a < array_to_play.length; a++) {
            if (array_to_play[i][a] == XO) {
                test = test + 1;
            }
        }
        return (test == size);
    }

    /** Проверка горизонталей */
    static boolean check_horizontal(char[][] array_to_play, int size, char XO, int i) {
        int test = 0;
        for (int a = 0; a < array_to_play.length; a++) {
            if (array_to_play[a][i] == XO) {
                test = test + 1;
            }
        }
        return (test == size);
    }

    /** Проверка прямой диагонали */
    static boolean check_diagonal(char[][] array_to_play, int size, char XO) {
        int test = 0;
        for (int a = 0; a < array_to_play.length; a++) {
            if (array_to_play[a][a] == XO) {
                test = test + 1;
            }
        }
        return (test == size);
    }

    /** Проверка обратной диагонали */
    static boolean check_diagonal_2(char[][] array_to_play, int size, char XO) {
        int test = 0;
        for (int a = 0; a < array_to_play.length; a++) {
            if (array_to_play[a][array_to_play.length - a - 1] == XO) {
                test = test + 1;
            }
        }
        return (test == size);
    }

    /** Проверка всех условий выигрыша */
    static boolean check_win(char[][] array_to_play, int size, char XO, int i) {

        return (check_vertical(array_to_play, size, XO, i) || check_horizontal(array_to_play, size, XO, i) ||
                check_diagonal(array_to_play, size, XO) || check_diagonal_2(array_to_play, size, XO));
    }

    /** Проверка на ничью */
    static boolean check_pat(char[][] array_to_play, int size) {
        int b = size * size;
        for (int i = 0; i < array_to_play.length; i++) {
            for (int j = 0; j < array_to_play[i].length; j++) {
                if (array_to_play[i][j] != get_array_symbol()) {
                    b = b - 1;
                }
            }
        }
        return (b == 0);
    }


    /** Печать поля для игры */
    static void print_array(char[][] array_to_play) {
        for (int i = 0; i < array_to_play.length; i++) {
            for (int j = 0; j < array_to_play.length; j++) {
                System.out.print(array_to_play[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Финальная проверка на выигрыш или ничью для игрока
     */
    static boolean check_player(char[][] array_to_play, int size) {
        for (int i = 0; i < array_to_play.length; i++) {
            if (check_win(array_to_play, size, 'X', i)) {
                System.out.println("Ты выиграл!!!");
                return true;
            }
        }
        if (check_pat(array_to_play, size)) {
            System.out.println("Увы, ничья!");
            return true;
        }
        return false;
    }

    /** Финальная проверка на выигрыш или ничью для компьютера */
    static boolean check_computer(char[][] array_to_play, int size) {
        for (int i = 0; i < array_to_play.length; i++) {
            if (check_win(array_to_play, size, '0', i)) {
                System.out.println("Ты проиграл!!!");
                return true;
            }
            if (check_pat(array_to_play, size)) {
                System.out.println("Увы, ничья!");
                return true;
            }
        }
        return false;
    }

}
