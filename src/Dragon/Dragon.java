package Dragon;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 *
 *Класс Дракон
 * Храниться в коллекции
 *
 *
 *
 */

public class Dragon implements ColorText, Comparable<Dragon>, Serializable {

    private static Integer ID_ALL = 0;
    private Integer id; //Значение поля должно быть больше 0
                        //Значение поля дожно быть уникальным
                        // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null
    private Color color; //Поле не может быть null
    private Coordinates coordinates;//Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля дожно генерироваться автоматически
    private int age; //Значение должно быть больше 0
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле может быть null



    public Dragon() {
        ID_ALL++;
        this.id = ID_ALL;
        this.creationDate = LocalDateTime.now();
    }

    /**
     *
     * @param name Имя дракона
     * @param age Возраст дракона > 0
     * @param color Enum, отвечающий за цвет
     * @param type Enum, отвечающий за тип
     * @param character Enum, отвечающий за характер
     *
     */
    public Dragon(String name, int age, Color color, DragonType type, DragonCharacter character) {
        this.name = name;
        this.color = color;
        this.age = age;
        this.character = character;
        this.type = type;
        ID_ALL++;
        this.id = ID_ALL;
        this.creationDate = LocalDateTime.now();
    }



//////////Геттеры и сеттеры
    public Integer getGlobalId(){
        return ID_ALL;
    }

    public void setGlobalId(int ID_ALL){
        this.ID_ALL = ID_ALL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DragonType getType() {
        return type;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter dragonCharacter) {
        this.character = dragonCharacter;
    }

    public DragonCave getCave() {
        return this.cave;
    }

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    public LocalDateTime GetTime(){
        return creationDate;
    }

    public void SetTime(LocalDateTime localDateTime){
        creationDate = localDateTime;
    }



////////

    /**
     * Нужен для сравнивания экзепляров коллекции
     *
     */
        public int compareTo(Dragon dr) {
            int result = this.name.compareTo(dr.name);
            if (result==0){
                result = this.coordinates.compareTo(dr.coordinates);
            }

            return result;
        }


    @Override
    public String toString(){
        return "Dragon{id = " + id + " name = " + ColorText.Text(name, color) + ", Creation time = " + creationDate + "} \n";
    }



    public String toDragonString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + ColorText.Text(name, color) + '\'' +
                ", age = "+ age + ",  color = " + color + ", type = " + type + ", character = " + character + ",\n cave = " + cave
                +", coordinates=" + coordinates.toString() + ", Creation Time = " + GetTime() + "}";
    }



    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        return false;
    }


    /**
     *
     * @param id
     * @throws IOException
     *
     * Нужен для создания нового экзепляра коллекции
     * @return dragon
     */
    public Dragon update(Integer id) throws IOException {
        System.out.println("Конструктор");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя Дракона");
        String str = scanner.nextLine();
        while (str.trim().equals("")||str.equals(null)){
            System.out.println("Имя не должно быть пустым или содержать только пробелы. Повторите ввод.");
            str = scanner.nextLine();
        }
        this.setName(str);

        System.out.println("Введите целый положительный возраст вашего дракона.");
        while (true){
            str = scanner.nextLine();
            if(str==null||str.trim().equals("")){
                System.out.println("Не верный формат ввода.");
                continue;
            }
            try{
                int number = Integer.parseInt(str);
                if(number> 0){
                    this.setAge(number);
                    break;
                }else{
                    System.out.println("Возраст должен быть больше нуля");
                }
            }catch (NumberFormatException e){
                System.out.println("Не верный формат ввода");
            }
        }

        System.out.println("Выберите цвет дракона. {RED, YELLOW, WHITE, BLUE, GREEN, PURPLE, CYAN}");
        while (true){
            str = scanner.nextLine();
            if((str.equals("color"))||(str.equals("\"color\""))){
                System.out.println("Доступные цвета: " +
                        "\nRED, YELLOW, WHITE, BLUE, BLACK, GREEN, PURPLE, CYAN");
                continue;
            }
            if(!isColor(str)||str.equals(null)||str.trim().equals("")){
                System.out.println("Не верный формат ввода.");
            }
            if(str.equals("RED") || str.equals("YELLOW") || str.equals("CYAN") || str.equals("WHITE") || str.equals("BLUE") || str.equals("BLACK") || str.equals("GREEN")||str.equals("PURPLE")){
                System.out.println("Цвет выбран " + str);
                setColor(Color.valueOf(str));
                break;
            }
            if(str.equals("x")){
                setColor(Color.WHITE);
                System.out.println("Цвет выбран WHITE");
                break;
            }
        }


        System.out.println("Выберите характер дракона. {CUNNING, EVIL, CHAOTIC_EVIL, FICKLE, UNKNOWN}");
        while (true){
            str = scanner.nextLine();
            if(str.equals("character") || str.equals("\"character\"")){
                System.out.println("Доступные характеристики: " +
                        "\nCUNNING, EVIL, CHAOTIC_EVIL, FICKLE, UNKNOWN");
                continue;
            }
            if(str.equals(null)||str.trim().equals("")){
                System.out.println("Не верный формат ввода.");
                continue;
            }
            if(!isCharacter(str)){
                System.out.println("Не верный формат ввода");
                continue;
            }

            if(str.equals("CUNNING") || str.equals("EVIL") || str.equals("CHAOTIC") || str.equals("FICKLE") || str.equals("UNKNOWN")){
                setCharacter(DragonCharacter.valueOf(str));
                break;
            }

            if(str.equals("x")){
                setCharacter(DragonCharacter.UNKNOWN);
                System.out.println("UNKNOWN");
                break;
            }
        }

        System.out.println("Введите тип Дракона. {WATER, UNDERGROUND, AIR, FIRE, EARTH, ICE, CHAOS, VOID}");
        while (true){
            str = scanner.nextLine();
            if(str.equals("type") || str.equals("\"type\"")){
                System.out.println("Доступные характеристики: " +
                        "\nWATER, UNDERGROUND, AIR, FIRE, EARTH, ICE, CHAOS, VOID");
                continue;
            }
            if(str==null||str.trim().equals("")){
                System.out.println("Не верный формат ввода.");
                continue;
            }
            if(str.equals("WATER") || str.equals("UNDERGROUND") || str.equals("AIR") || str.equals("FIRE") || str.equals("EARTH") || str.equals("ICE") || str.equals("CHAOS") || str.equals("VOID")){
                setType(DragonType.valueOf(str));
                break;
            }else{
                System.out.println("Неверный ввод");
            }
            if(str.equals("x")){
                setType(DragonType.CHAOS);
                System.out.println("CHAOS");
                break;
            }
        }

        System.out.println("Введите координаты x и y через запятую");
        Integer x = null;
        Integer y = null;
        while (x==null||y==null) {
            try {
                String[] coords = scanner.nextLine().split(",");
                x = parseInt(coords[0]);
                y = parseInt(coords[1]);
            } catch (Exception e) {
                System.out.println("Данные введены некорректно. Повторите ввод.");
            }
        }
        this.setCoordinates( new Coordinates(x,y));


        System.out.println("Введите глубину пещеры.");
        System.out.println("Учитываются либо пустое значения, либо значение больше или равное нулю.");
        DragonCave dragonCave = new DragonCave();
        boolean bool = false;
        while(true){
           str = scanner.nextLine();
            if(str.equals("")|| str.equals(null)){
                Float depth = 0.1F;
                dragonCave.setDepth(depth);
                setCave(dragonCave);
                System.out.println("Вы ввели пустое поле. Пещера будет null");
                bool = true;
                break;
            }
            try{
                Float number = Float.parseFloat(str);
                if(number >= 0 ){
                    dragonCave.setDepth(number);
                    this.setCave(dragonCave);
                    break;
                }else{
                    System.out.println("Число должно быть больше нуля");
                }
            }catch (NumberFormatException e){
                System.out.println("Не верный формат ввода");
            }
        }

        this.setId(id);
        //System.out.println("Дракон " + name + " создан.");
        return null;
    }


    /**
     * Класс нужен для проверки enum
     *
     * @param str
     * @return boolean
     *
     */
    public static boolean isColor(String str){
        try{
            Color color = Color.valueOf(str);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    /**
     * Класс нужен для проверки enum
     *
     * @param str
     * @return
     */
    public static boolean isCharacter(String str){
        try{
            DragonCharacter dragonCharacter = DragonCharacter.valueOf(str);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

}
