package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import uet.oop.bomberman.ai.AIMedium;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.*;
import java.util.List;

public class BombermanGame extends Application {
    public static Random random = new Random();

    private GraphicsContext gc;
    private Canvas canvas;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public static List<Enemy> enemyList = new ArrayList<>();
    public static List<Portal> portalList = new ArrayList<>();
    public static List<Flame> explosivesList = new ArrayList<>();

    private List<Brick> brickList = new ArrayList<>();
    private List<Bomb> bombList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();

    public static int numberBomb = 0;
    public static int numberEnemy = 0;

    private static int _itemSpeed = 0;
    private static int _itemFlame = 0;
    private static int _itemBomb = 0;
    private static int _itemblood = 0;
    public static int _itemtime = 0;

    public static Integer time = 200;
    public static int level = 1;

    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static int lives = 100;
    public static Label scoreText = new Label();
    public static Label live = new Label();
    public static Label timerr = new Label();
    public static int score = 0;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void start(Stage primaryStage){
        try {
            InputStream inp =new FileInputStream("res/media/maii.wav");
            AudioStream au = new AudioStream(inp);
            AudioPlayer.player.start(au);
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Double's Bomberman version 2021.1");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void clickMenu2(ActionEvent events) {
        try {
            score = 0;
            Bomber.step = 2;
            portalList.clear();
            stillObjects.clear();
            brickList.clear();
            bombList.clear();
            itemList.clear();
            entities.clear();
            enemyList.clear();
            explosivesList.clear();
            //numberDead = 0;
            numberEnemy = 0;
            _itemSpeed = 0;
            _itemFlame = 0;
            _itemBomb = 0;
            _itemblood = 0;
            _itemtime = 0;
            time = 200;
            lives = 100;
            Flame.widen = 0;
            Bomb.canPutBomb = 1;
            level = 1;
            Map.insertFromFile();
            createMap();
            bomberman.setAgain();

            Stage stage = (Stage)((Node) events.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickNext(ActionEvent events) throws IOException {
        score = 0;
        level = 2;
        portalList.clear();
        stillObjects.clear();
        brickList.clear();
        bombList.clear();
        itemList.clear();
        entities.clear();
        enemyList.clear();
        explosivesList.clear();
        //numberDead = 0;
        numberEnemy = 0;
        _itemSpeed = 0;
        _itemFlame = 0;
        _itemBomb = 0;
        _itemblood = 0;
        _itemtime = 0;
        time = 200;
        lives = 100;
        Flame.widen = 0;
        Bomber.step = 2;
        Bomb.canPutBomb = 1;
        bomberman.setAgain();
        clickPlay(events);

        Enemy bear = new Bear(21,3,Sprite.bear_right1.getFxImage());
        enemyList.add(bear);
        numberEnemy++;
        entities.add(bear);

        Enemy balloommap2 = new Ballom(9,5 , Sprite.balloom_right2.getFxImage( ));
        enemyList.add(balloommap2);
        numberEnemy++;
        entities.add(balloommap2);

        Enemy ghostmap2 = new Ghost(21,9,Sprite.oneal_right1.getFxImage());
        enemyList.add(ghostmap2);
        numberEnemy++;
        entities.add(ghostmap2);

        Enemy red = new Red(25,6,Sprite.bear_right1.getFxImage());
        enemyList.add(red);
        numberEnemy++;
        entities.add(red);

        Enemy orange = new Orange(11,9,Sprite.orange_left1.getFxImage());
        enemyList.add(orange);
        numberEnemy++;
        entities.add(orange);

        createMap();
        update();
        render();

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(random.nextInt(2) + 1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                               // ((Ballom) balloommap2).direct = random.nextInt(4);
                                ((Ghost) ghostmap2).direct = random.nextInt(4);
                                Oneal.step = random.nextInt(2)+1;
                                ((Bear) bear).direct = random.nextInt(4);
                                ((Red) red).direct = new AIMedium(bomberman,red).calculateDirection();
                                ((Orange) orange).direct = random.nextInt(4);
                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    public void clickPlay(ActionEvent events) throws IOException {
        Map.insertFromFile();
        Stage mainStage = (Stage)((Node) events.getSource()).getScene().getWindow();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Map.WIDTH, Sprite.SCALED_SIZE * Map.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(scoreText);
        root.getChildren().add(live);
        root.getChildren().add(timerr);

        // Tao scene
        Scene scene = new Scene(root);

        Image image = new Image("sprites/blood.png");
        ImageView imageView = new ImageView(image);

        live.setFont(new Font("Monospaced", 24));
        live.setTextFill(Color.web("BLACK"));
        live.setText("Máu " + lives);

        live.setTranslateX(300);
        live.setGraphic(imageView);

        timerr.setFont(new Font("Monospaced", 24));
        timerr.setText("Time " + time);
        timerr.setTranslateX(700);
        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        if (t != null) {
            t.stop();
        }
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                time--;
                timerr.setText("Time " + time);
                if (time <= 0) {
                    t.stop();
                }
            }
        });
        t.getKeyFrames().add(keyFrame);
        t.playFromStart();


        score = 0;
        scoreText.setFont(new Font("Cambria", 24));
        scoreText.setTextFill(Color.web("BLACK"));
        scoreText.setText("SCORE : " + score);

        Enemy ballom1 = new Ballom(11 , 1 , Sprite.balloom_left1.getFxImage());
        Enemy oneal1 = new Oneal(10 , 5 , Sprite.oneal_right1.getFxImage());
        Enemy doll1 = new Doll(21 , 9 , Sprite.doll_right1.getFxImage());
        Enemy kon1 = new Kondoria(26,5,Sprite.kondoria_right1.getFxImage());
        Enemy minvo1 = new Minvo(29,2,Sprite.minvo_left1.getFxImage());
        Enemy ghost1 = new Ghost(6,11,Sprite.ghost_right1.getFxImage());

        if (level == 1) {
            enemyList.add(ballom1);
            numberEnemy++;
            enemyList.add(oneal1);
            numberEnemy++;
            enemyList.add(doll1);
            numberEnemy++;
            enemyList.add(kon1);
            numberEnemy++;
            enemyList.add(minvo1);
            numberEnemy++;
            enemyList.add(ghost1);
            numberEnemy++;
        }

        entities.add(bomberman);

        if (level == 1) {
            entities.add(ballom1);
            entities.add(oneal1);
            entities.add(doll1);
            entities.add(kon1);
            entities.add(minvo1);
            entities.add(ghost1);
        }

        mainStage.setScene(scene);
        mainStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT) ) {

                ((Bomber) bomberman).num = 1;
            }
            if (e.getCode() .equals(KeyCode.LEFT) ) {

                ((Bomber) bomberman).num = 2;
            }
            if (e.getCode() .equals(KeyCode.UP) ) {

                ((Bomber) bomberman).num = 3;
            }
            if (e.getCode() .equals(KeyCode.DOWN) ) {

                ((Bomber) bomberman).num = 4;
            }
            if (e.getCode().equals(KeyCode.K)) {
                Flame.widen = 1;
            }
            if (e.getCode().equals(KeyCode.D)) {
                Bomber.step = 1;
            }
            if (e.getCode().equals(KeyCode.SPACE)) {
                if (numberBomb < Bomb.canPutBomb){
                    numberBomb++;
                    Bomb bomb = new Bomb(bomberman.getXTile(), bomberman.getYTile(), Sprite.bomb.getFxImage());
                    Bomb.isBomb = true;
                    bombList.add(bomb);
                    Timer timer10 = new Timer();
                    timer10.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Map.ar[bomb.getYTile()][bomb.getXTile()] = ' ';
                        }
                    },1000);
                    bomb.exploding();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int a = bombList.get(0).getXTile();
                            int b = bombList.get(0).getYTile();
                            bombList.remove(0);
                            Flame.expandFlame(Flame.widen, a, b);
                            try {
                                InputStream inp =new FileInputStream("res/media/bomb.wav");
                                AudioStream au = new AudioStream(inp);
                                AudioPlayer.player.start(au);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                           /* for (int i = 0 ;i < explosivesList.size();i++) {
                                if (bomberman.getXTile() == explosivesList.get(i).getXTile()
                                        && bomberman.getYTile() == explosivesList.get(i).getYTile()) {
                                    bomberman.kill();
                                    timer.cancel();
                                }
                            }*/

                            Timer timer1 = new Timer();
                            timer1.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < explosivesList.size(); i++) {
                                        int a = explosivesList.get(i).getXTile();
                                        int b = explosivesList.get(i).getYTile();
                                        for (int j = 0; j < brickList.size(); j++) {
                                            if (brickList.get(j).getXTile() == a && brickList.get(j).getYTile() == b && Map.ar[b][a] == '*') {
                                                brickList.get(j).exploreBrick();
                                                Timer timer2 = new Timer();
                                                int posFl = j;
                                                timer2.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        Map.ar[b][a] = ' ';
                                                        if (posFl == 16) {
                                                            portalList.add(new Portal(a, b, Sprite.portal.getFxImage()));
                                                        } else if (posFl == 10 || posFl == 36) {
                                                            _itemSpeed ++;
                                                            itemList.add(new Item(a, b, Sprite.powerup_speed.getFxImage()));
                                                        } else if (posFl == 27 || posFl == 34) {
                                                            _itemBomb ++;
                                                            itemList.add(new Item(a, b, Sprite.powerup_bombs.getFxImage()));
                                                        } else if (posFl == 0 || posFl == 23) {
                                                            _itemFlame ++;
                                                            itemList.add(new Item(a, b, Sprite.powerup_flames.getFxImage()));
                                                        } else if (posFl == 28) {
                                                            _itemblood ++;
                                                            itemList.add(new Item(a, b, Sprite.powerup_detonator.getFxImage()));
                                                        } else if (posFl == 15) {
                                                            _itemtime ++;
                                                            itemList.add(new Item(a, b, Sprite.powerup_wallpass.getFxImage()));
                                                        }
                                                        else {
                                                            brickList.set(posFl, new Brick(a, b, Sprite.grass.getFxImage()));
                                                        }
                                                    }
                                                }, 800);
                                            }
                                        }
                                    }
                                    Map.ar[b][a] = ' ';
                                    explosivesList.clear();
                                    numberBomb--;
                                }
                            }, 400);
                        }
                    }, 3000);

                }

            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                ((Bomber) bomberman).num = 0;
            }
            if (e.getCode().equals(KeyCode.LEFT)) {
                ((Bomber) bomberman).num = 0;
            }
            if (e.getCode().equals(KeyCode.UP)) {
                ((Bomber) bomberman).num = 0;
            }
            if (e.getCode().equals(KeyCode.DOWN)) {
                ((Bomber) bomberman).num = 0;
            }
            if (e.getCode().equals(KeyCode.SPACE)) {
                Bomb.isBomb = false;
            }
        });
        // Them scene vao stage
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
                boolean er = false;
                // Bomber va chạm enemy
                for(int i = 0; i < enemyList.size(); i++) {
                    if (enemyList.get(i).getXTile() == bomberman.getXTile()
                            && enemyList.get(i).getYTile() == bomberman.getYTile()) {
                        bomberman.kill();
                        er = true;
                    }
                    if (er == true) {
                        lives -= 1;
                        live.setText("Máu " + lives);
                        live.setGraphic(imageView);
                        Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (lives > 1)
                                    bomberman.setAgain();

                            }
                        }, 700);
                    }
                }

                //Enemy bị bắn trúng
                if (explosivesList.size()!=0) {
                    for (int i = 0 ;i < explosivesList.size();i++) {
                        if (bomberman.getXTile() == explosivesList.get(i).getXTile()
                                && bomberman.getYTile() == explosivesList.get(i).getYTile()) {
                            bomberman.kill();
                            lives -= 1;
                            live.setText("Máu " + lives);
                            Timer timer2 = new Timer();
                            timer2.schedule(new TimerTask() {
                                @Override
                                public void run() {


                                    if (lives > 0) {
                                        bomberman.setAgain();
                                    }
                                }
                            }, 800);

                        }
                    }
                    for (int i = 0 ; i < explosivesList.size();i++){
                        int a_ = explosivesList.get(i).getXTile();
                        int b_ = explosivesList.get(i).getYTile();
                        for(int j = 0; j < enemyList.size();j++ ) {
                            int x_ = enemyList.get(j).getXTile();
                            int y_ = enemyList.get(j).getYTile();
                            if (x_ == a_ && y_ == b_) {
                                enemyList.get(j).kill();
                                try{
                                    InputStream inp =new FileInputStream("res/media/enemyscream.wav");
                                    AudioStream au = new AudioStream(inp);
                                    AudioPlayer.player.start(au);}
                                catch (Exception q) {
                                    System.out.println(q.getMessage());
                                }
                                enemyList.remove(j);
                            }
                        }
                    }
                }

                for (int i = 0; i < itemList.size();i++){
                    //Bomber ăn speed item
                    if (!itemList.isEmpty() && bomberman.getXTile() == itemList.get(i).getXTile()
                            && bomberman.getYTile() == itemList.get(i).getYTile() && _itemSpeed != 0 ){
                        try {
                            InputStream inp = new FileInputStream("res/media/item.wav");
                            AudioStream au = new AudioStream(inp);
                            AudioPlayer.player.start(au);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        score += 160;
                        scoreText.setText("SCORE: " + score);
                        live.setText("Máu " + lives);
                        itemList.set(i, new Item(itemList.get(i).getXTile(), itemList.get(i).getYTile() , Sprite.grass.getFxImage()));
                        itemList.remove(i);
                        _itemSpeed--;
                        Bomber.step = 2 * Bomber.step;
                    }

                    //Bomber ăn Flame item
                    else if (!itemList.isEmpty() && bomberman.getXTile() == itemList.get(i).getXTile()
                            && bomberman.getYTile() == itemList.get(i).getYTile() && _itemFlame != 0) {
                        try {
                            InputStream inp = new FileInputStream("res/media/item.wav");
                            AudioStream au = new AudioStream(inp);
                            AudioPlayer.player.start(au);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        score += 180;
                        scoreText.setText("SCORE: " + score);
                        live.setText("Máu " + lives);
                        itemList.set(i, new Item(itemList.get(i).getXTile(), itemList.get(i).getYTile() , Sprite.grass.getFxImage()));
                        itemList.remove(i);
                        _itemFlame--;
                        Flame.widen++ ;
                    }

                    //Bomber ăn Bomb item
                    else if( !itemList.isEmpty() && bomberman.getXTile() == itemList.get(i).getXTile()
                            && bomberman.getYTile() == itemList.get(i).getYTile() && _itemBomb != 0) {
                        try {
                            InputStream inp = new FileInputStream("res/media/item.wav");
                            AudioStream au = new AudioStream(inp);
                            AudioPlayer.player.start(au);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        score += 180;
                        scoreText.setText("SCORE: " + score);
                        live.setText("Máu " + lives);
                        itemList.set(i, new Item(itemList.get(i).getXTile(), itemList.get(i).getYTile() , Sprite.grass.getFxImage()));
                        itemList.remove(i);
                        _itemBomb--;
                        Bomb.canPutBomb++ ;
                    }

                    //Bomber ăn Blood item
                    else if( !itemList.isEmpty() && bomberman.getXTile() == itemList.get(i).getXTile()
                            && bomberman.getYTile() == itemList.get(i).getYTile() && _itemblood != 0) {
                        try {
                            InputStream inp = new FileInputStream("res/media/item.wav");
                            AudioStream au = new AudioStream(inp);
                            AudioPlayer.player.start(au);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        score += 150;
                        scoreText.setText("SCORE: " + score);
                        int a = itemList.get(i).getXTile();
                        int b = itemList.get(i).getYTile();
                        itemList.set(i, new Item(a, b, Sprite.grass.getFxImage()));
                        itemList.remove(i);
                        _itemblood--;
                        lives += 70;
                        live.setText("Máu " + lives);
                    }

                    //Bomber ăn Time item
                    else if( !itemList.isEmpty() && bomberman.getXTile() == itemList.get(i).getXTile()
                            && bomberman.getYTile() == itemList.get(i).getYTile() && _itemtime != 0) {
                        try {
                            InputStream inp = new FileInputStream("res/media/item.wav");
                            AudioStream au = new AudioStream(inp);
                            AudioPlayer.player.start(au);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        score += 170;
                        scoreText.setText("SCORE: " + score);
                        int a = itemList.get(i).getXTile();
                        int b = itemList.get(i).getYTile();
                        itemList.set(i, new Item(a, b, Sprite.grass.getFxImage()));
                        itemList.remove(i);
                        _itemtime--;
                        time += 70;
                        timerr.setText("Time " + time);
                    }
                }

                //Thắng level 1
                if (numberEnemy == 0  && bomberman.getXTile() == 11 && bomberman.getYTile() == 3 && level == 1 ){
                    score += 1000;
                    scoreText.setText("SCORE: " + score);
                    try {
                        score += 1000;
                        scoreText.setText("SCORE: " + score);
                        InputStream inp =new FileInputStream("res/media/maii.wav");
                        AudioStream au = new AudioStream(inp);
                        AudioPlayer.player.start(au);
                        Parent root = FXMLLoader.load(getClass().getResource("/level2.fxml"));
                        Scene scene = new Scene(root);
                        mainStage.setScene(scene);
                        mainStage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    level++;
                }

                //Thắng level 2
                if (numberEnemy == 0  && bomberman.getXTile() == 4 && bomberman.getYTile() == 3 && level == 2){
                    score += 1000;
                    scoreText.setText("SCORE: " + score);
                    try {
                        InputStream inp =new FileInputStream("res/media/maii.wav");
                        AudioStream au = new AudioStream(inp);
                        AudioPlayer.player.start(au);
                        Parent root = FXMLLoader.load(getClass().getResource("/win.fxml"));
                        Scene scene = new Scene(root);
                        mainStage.setScene(scene);
                        mainStage.show();
                        level++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (lives < 0 || time == 0){
                    try {
                        InputStream inp =new FileInputStream("res/media/game_over.wav");
                        AudioStream au = new AudioStream(inp);
                        AudioPlayer.player.start(au);
                        Parent root = FXMLLoader.load(getClass().getResource("/dead.fxml"));
                        Scene scene = new Scene(root);
                        mainStage.setScene(scene);
                        mainStage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    lives = 5;
                    time = 1;
                }
            }
        };
        timer.start();
        createMap();
        render();

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(random.nextInt(2) + 1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                               // ((Ballom) ballom1).direct =  random.nextInt(4);
                                Oneal.step = random.nextInt(2) + 1;
                                ((Oneal) oneal1).direct = new AIMedium(bomberman, oneal1).calculateDirection();
                                Oneal.direct = random.nextInt(4);
                                Doll.step = random.nextInt(3) + 1;
                                ((Doll) doll1).direct = random.nextInt(4);
                                ((Kondoria) kon1).direct = new AIMedium(bomberman, kon1).calculateDirection();
                                ((Minvo) minvo1).direct = random.nextInt(2) + 2;
                                Minvo.step = random.nextInt(2) + 1;
                                ((Ghost) ghost1).direct = random.nextInt(4);
                                Ghost.step = random.nextInt(1) + 2;
                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    public void clickMenuDead(ActionEvent events) {
        try {
            score = 0;
            Bomber.step = 2;
            portalList.clear();
            stillObjects.clear();
            brickList.clear();
            bombList.clear();
            itemList.clear();
            entities.clear();
            enemyList.clear();
            explosivesList.clear();
            //numberDead = 0;
            numberEnemy = 0;
            _itemSpeed = 0;
            _itemFlame = 0;
            _itemBomb = 0;
            lives = 100;
            time = 200;
            Flame.widen = 0;
            Bomber.step = 2;
            Bomb.canPutBomb = 1;
            level = 1;
            Map.insertFromFile();
            createMap();
            bomberman.setAgain();

            Stage stage = (Stage)((Node) events.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickPlayAgain(ActionEvent event) throws IOException {
        score = 0;
        portalList.clear();
        stillObjects.clear();
        brickList.clear();
        bombList.clear();
        itemList.clear();
        entities.clear();
        enemyList.clear();
        explosivesList.clear();
        //numberDead = 0;
        numberEnemy = 0;
        _itemSpeed = 0;
        _itemFlame = 0;
        _itemBomb = 0;
        lives = 100;
        time = 200;
        Flame.widen = 0;
        Bomber.step = 2;
        Bomb.canPutBomb = 1;
        Map.insertFromFile();
        createMap();
        bomberman.setAgain();
        if (level == 1) {
            clickPlay(event);
        } else if (level == 2) {
            clickNext(event);
        }
    }

    public void clickIns(ActionEvent event) {
        try {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/instruct.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickMenuIns(ActionEvent events) {
        try {
            Stage stage = (Stage)((Node) events.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickInf(ActionEvent event) {
        try {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/infor.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickBackInf(ActionEvent event) {
        try {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickMenuWin(ActionEvent events) {
        try {
            score = 0;
            Bomber.step = 2;
            portalList.clear();
            stillObjects.clear();
            brickList.clear();
            bombList.clear();
            itemList.clear();
            entities.clear();
            enemyList.clear();
            //numberDead = 0;
            numberEnemy = 0;
            _itemSpeed = 0;
            _itemFlame = 0;
            _itemBomb = 0;
            _itemblood = 0;
            _itemtime = 0;
            time = 200;
            lives = 100;
            Flame.widen = 0;
            Bomber.step = 2;
            Bomb.canPutBomb = 1;
            level = 1;
            Map.insertFromFile();
            createMap();
            bomberman.setAgain();

            Stage stage = (Stage)((Node) events.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            Parent mainGUI = loader.load();
            Scene scene = new Scene(mainGUI);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickLevel1(ActionEvent events) throws IOException {
        score = 0;
        level = 1;
        clickPlayAgain(events);
    }

    public void clickLevel2(ActionEvent events) throws IOException {
        score = 0;
        level = 2;
        clickPlayAgain(events);
    }

    public void createMap() {
        for (int i = 0; i < Map.HEIGHT; i++) {
            for (int j = 0; j < Map.WIDTH; j++) {
                if (Map.ar[i][j] == '#') {
                    stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else if (Map.ar[i][j] == '*') {
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    brickList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                }
                else if (Map.ar[i][j] == ' ') {
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                } else {
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bombList.forEach(Bomb::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        brickList.forEach(g -> g.render(gc));
        portalList.forEach(g -> g.render(gc));
        if (!itemList.isEmpty()) itemList.forEach(g -> g.render(gc));
        if (!bombList.isEmpty()) bombList.forEach(g -> g.render(gc));
        if (!explosivesList.isEmpty()) explosivesList.forEach(g -> g.render(gc));
        if (!enemyList.isEmpty()) enemyList.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
