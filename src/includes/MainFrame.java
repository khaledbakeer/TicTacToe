/******************************************************************************
 * MIT License                                                                *
 *                                                                            *
 * Copyright (c) 2018 Khaled Bakeer                                           *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  *
 * copies of the Software, and to permit persons to whom the Software is      *
 * furnished to do so, subject to the following conditions:                   *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.                            *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,   *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE*
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER     *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.                                                                  *
 ******************************************************************************/

package includes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TicTacToe.
 *
 * @author Khaled Bakeer
 * @version 0.1
 *      <p>X|O|X</p>
 *      <p>O|X|O</p>
 *      <p>X|O|X</p>
 * <h3>Functions in this Game:</h3>
 * <ol>
 *      <li>Two Player Game</li>
 *      <li>Now Playing: Show The Player Name {@link #playerName}, {@link #oPlayerName}, {@link #xPlayerName}</li>
 *      <li>Undo one Time: Go one move back! {@link #undoGame}</li>
 *      <li>New Game: Load everything from the beginning</li>
 *      <li>Load Saved Game: Load Saved Game from "recources/data.xml" {@link #btnLoadNewGame} {@link #loadNewGame()}</li>
 *      <li>Save Game: Save all Movements in "recources/data.xml" {@link #saveGame} {@link #saveGameAsXML()}</li>
 *      <li>Exit Game: Close the Windows {@link #exitGame} {@link #exitGameNow()}</li>
 *      <li>List of all Movements: {@link #lblLog} {@link #lblLogModel}</li>
 * </ol>
 */
public class MainFrame extends JFrame implements ActionListener {

    private int posX;
    private int posY;

    private Integer n = 0;

    private JList lblLog;
    private DefaultListModel<String> lblLogModel;
    private Object[][] data = new Object[3][3];
    private Object[][] loadData = new Object[3][3];

    private JButton[][] btn = new JButton[3][3];

    private JButton btnLoadNewGame;
    private JButton saveGame;
    private JButton loadSavedGame;
    private JButton exitGame;
    private JButton undoGame;

    private JLabel playerName;

    private Color mainColor = new Color(175, 219, 216);

    private String xPlayerName;
    private String oPlayerName;

    private String ticTitle = "TicTacToe";
    private String xWinner = "X is the Winner. Do you want to play again?";
    private String oWinner = "O is the Winner. Do you want to play again?";
    private String noWinner = "No Winner :-( Do you want to play again?";

    /**
     * Load The Main Frame.
     *
     * <ol>
     *      <li>Init. Frame {@link #initFrame()}</li>
     *      <li>Init. Components {@link #initComponents()}</li>
     *      <li>Init. Load New Game {@link #loadNewGame()}</li>
     * </ol>
     */
    public MainFrame() {
        initFrame();
        initComponents();
        loadNewGame();
    }

    /**
     * Frame Initialisation.
     *
     * <ol>
     *      <li>Set Default Close Operation</li>
     *      <li>Set Bounds</li>
     *      <li>Not Resizable</li>
     *      <li>Set Title</li>
     *      <li>Set Color</li>
     *      <li>Set Undecorated</li>
     *      <li>Set Frame On Center {@link #frameOnCenter()}</li>
     *      <li>Set Layout</li>
     * </ol>
     */
    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(150, 150, 630, 470);
        setResizable(false);
        setTitle("TicTacToe");
        getContentPane().setBackground(mainColor);
        setUndecorated(true);
        frameOnCenter();
        setLayout(null);
    }

    /**
     * Set Frame to appear centered on the screen
     */
    private void frameOnCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    /**
     * Initialisierung aller Componente 1- 9 Butons of X, O JList Bewegungssensor
     */
    private void initComponents() {
        // Init. X,O Buttons
        initXOButtons();
        // Init. list of Movements (Clicks)
        initListOfMovements();
        // Init Label Player Name
        initPlayerNameLabel();
        // Init. Undo Button
        initUndoButton();
        // Init. load new game button
        initLoadNewGameButton();
        // Init. load saved game button
        initLoadSavedGameButton();
        // Init. save game button
        initSaveGameButton();
        // Init. exit game button
        initExitGameButton();
    }

    /**
     * Init. exit game button
     */
    private void initExitGameButton() {
        exitGame = new JButton("Exit Game");
        exitGame.setBounds(470, 210, 150, 30);
        exitGame.setEnabled(true);
        exitGame.setBackground(mainColor);
        exitGame.addActionListener(this);
        add(exitGame);
    }

    /**
     * Init. save game button
     */
    private void initSaveGameButton() {
        // Save Game:
        saveGame = new JButton("Save Game");
        saveGame.setBounds(470, 170, 150, 30);
        saveGame.setEnabled(true);
        saveGame.setBackground(mainColor);
        saveGame.addActionListener(this);
        add(saveGame);
    }

    /**
     * Init. load saved game button
     */
    private void initLoadSavedGameButton() {
        loadSavedGame = new JButton("Load Saved Game");
        loadSavedGame.setBounds(470, 130, 150, 30);
        loadSavedGame.setEnabled(true);
        loadSavedGame.setBackground(mainColor);
        loadSavedGame.addActionListener(this);
        add(loadSavedGame);
    }

    /**
     * Init. load new game button
     */
    private void initLoadNewGameButton() {
        btnLoadNewGame = new JButton("New Game");
        btnLoadNewGame.setBounds(470, 90, 150, 30);
        btnLoadNewGame.setEnabled(true);
        btnLoadNewGame.setBackground(mainColor);
        btnLoadNewGame.addActionListener(this);
        add(btnLoadNewGame);
    }

    /**
     * Init. Undo Button
     */
    private void initUndoButton() {
        undoGame = new JButton("Undo one Time");
        undoGame.setBounds(470, 50, 150, 30);
        undoGame.setEnabled(false);
        undoGame.addActionListener(this);
        undoGame.setBackground(mainColor);
        add(undoGame);
    }

    /**
     * Init Label Player Name
     */
    private void initPlayerNameLabel() {
        playerName = new JLabel("Now Playing: " + getXPlayerName());
        playerName.setBounds(470, 10, 150, 30);
        add(playerName);
    }

    /**
     * Init. list of Movements (Clicks)
     */
    private void initListOfMovements() {
        lblLogModel = new DefaultListModel<>();
        lblLog = new JList(lblLogModel);
        lblLog.setBounds(470, 300, 150, 170);
        lblLog.setBackground(mainColor);
        add(lblLog);
    }

    /**
     * Init. X,O Buttons
     */
    private void initXOButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int u = 150;
                btn[i][j] = new JButton();
                btn[i][j].setBounds(10 + u * j, 10 + u * i, u, u);
                btn[i][j].addActionListener(this);
                btn[i][j].setFont(new Font("Tahoma", Font.BOLD, 72));
                btn[i][j].setBackground(mainColor);
                add(btn[i][j]);
            }
        }
    }

    /**
     * Make main frame visible
     */
    public void showMainFrame() {
        setVisible(true);
    }

    /**
     * @return Integer n: Nummer or the counter of all Movements
     */
    private Integer getN() {
        return n;
    }

    /**
     * @param n Set the nummer of the movement
     */
    private void setN(Integer n) {
        this.n = n;
    }

    /**
     * Show Dialog Message with Messages like {@link #xWinner}, {@link #oWinner} or {@link #noWinner}
     * If YES is clicked Load New Game {@link #loadNewGame()}
     * Else Exit Game {@link #exitGameNow()}
     *
     * @param msg String
     */
    private void showMSGYesNo(String msg) {
        int reply = JOptionPane.showConfirmDialog(null, msg, ticTitle, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            loadNewGame();
        } else {
            exitGameNow();
        }
    }

    /**
     * Get message box: congratulations for X Player {@link #showMSGYesNo(String)}
     */
    private void msgBoxXWinner() {
        showMSGYesNo(xWinner);
    }

    /**
     * Get message box: congratulations for O Player {@link #showMSGYesNo(String)}
     */
    private void msgBoxOWinner() {
        showMSGYesNo(oWinner);
    }

    /**
     * Get message box: No Winner! {@link #showMSGYesNo(String)}
     */
    private void msgBoxNoWinner() {
        showMSGYesNo(noWinner);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == btn[i][j]) {
                    setN(getN() + 1);
                    addXO(i, j);
                    btn[i][j].setEnabled(false);
                    btn[i][j].setText(XorO());
                    checkWinner(i, j);
                } // End If
            } // End For j
        } // End For i

        // Load new game:
        if (e.getSource() == btnLoadNewGame) {
            loadNewGameMethod();
        }

        // Exit Game:
        if (e.getSource() == exitGame) {
            exitGameNow();
        }

        // Undo the last Movement
        if (getN() > 0) {
            undoGame.setEnabled(true);
            if (e.getSource() == undoGame) {
                undoGameMethod();
            }
        }

        // Save Game
        if (e.getSource() == saveGame) {
            saveGameMethod();
        }

        // Load Saved Game
        if (e.getSource() == loadSavedGame) {
            openGameFromXML();
        }


    }

    /**
     * Save Game Method:
     * <ol>
     *      <li>Save Game as XML {@link #saveGameAsXML()}</li>
     *      <li>Show Message</li>
     * </ol>
     */
    private void saveGameMethod() {
        saveGameAsXML();
        JOptionPane.showMessageDialog(null, "Game saved!");
    }

    /**
     * Load New Game Method:
     * <ol>
     *      <li>Show Message</li>
     *      <li>Load new Game {@link #loadNewGame()}</li>
     * </ol>
     */
    private void loadNewGameMethod() {
        int reply = JOptionPane.showConfirmDialog(null, "Realy! Do you want to start again!", ticTitle, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            loadNewGame();
        }
    }

    /**
     * Undo Game Method:
     * <ol>
     *     <li>Set n the Counter -1 {@link #setN(Integer)} to be acceptable with the indexing of the model {@link #lblLogModel} and the object {@link #data}</li>
     *     <li>Enable the last clicked Button, und set its text to "" then set the player name back using {@link #XorO()}</li>
     *     <li>Disable the Undo Button again</li>
     * </ol>
     */
    private void undoGameMethod() {
        setN(getN() - 1);
        lblLogModel.removeElementAt(lblLogModel.lastIndexOf(lblLogModel.lastElement()));
        data[posX][posY] = null;
        btn[getPosX()][getPosY()].setEnabled(true);
        btn[getPosX()][getPosY()].setText("");
        XorO();
        undoGame.setEnabled(false);
    }

    /**
     * Check The Winner (All Situations)
     *
     * <ol>
     *      <li>If 3 Xs are a raw {@link #msgBoxXWinner()}</li>
     *      <li>If 3 Os are a raw {@link #msgBoxOWinner()}</li>
     *      <li>If 3 Xs are a column {@link #msgBoxXWinner()}</li>
     *      <li>If 3 Os are a column {@link #msgBoxOWinner()}</li>
     *      <li>If 3 Xs are a 3 points of a line from the top left till the buttom of the right side {@link #msgBoxXWinner()}</li>
     *      <li>If 3 Os are a 3 points of a line from the top left till the buttom of the right side {@link #msgBoxOWinner()}</li>
     *      <li>If 3 Xs are a 3 points of a line from the top right till the buttom of the left side {@link #msgBoxXWinner()}</li>
     *      <li>If 3 Os are a 3 points of a line from the top right till the buttom of the left side {@link #msgBoxOWinner()}</li>
     *      <li>If there are no winners {@link #msgBoxNoWinner()}</li>
     * </ol>
     *
     * @param i Integer, x Axis
     * @param j Integer, y Axis
     */
    private void checkWinner(int i, int j) {

        // If 3 Xs are a raw
        if (btn[i][0].getText() == "X" & btn[i][1].getText() == "X" & btn[i][2].getText() == "X") {
            msgBoxXWinner();
        }

        // If 3 Os are a raw
        if (btn[i][0].getText() == "O" & btn[i][1].getText() == "O" & btn[i][2].getText() == "O") {
            msgBoxOWinner();
        }

        // If 3 Xs are a column
        if (btn[0][j].getText() == "X" & btn[1][j].getText() == "X" & btn[2][j].getText() == "X") {
            msgBoxXWinner();
        }

        // If 3 Os are a column
        if (btn[0][j].getText() == "O" & btn[1][j].getText() == "O" & btn[2][j].getText() == "O") {
            msgBoxOWinner();
        }

        // If 3 Xs are a 3 points of a line from the top left till the buttom of the right side
        if (btn[0][0].getText() == "X" & btn[1][1].getText() == "X" & btn[2][2].getText() == "X") {
            msgBoxXWinner();
        }

        // If 3 Os are a 3 points of a line from the top left till the buttom of the right side
        if (btn[0][0].getText() == "O" & btn[1][1].getText() == "O" & btn[2][2].getText() == "O") {
            msgBoxOWinner();
        }

        // If 3 Xs are a 3 points of a line from the top right till the buttom of the left side
        if (btn[2][0].getText() == "X" & btn[1][1].getText() == "X" & btn[0][2].getText() == "X") {
            msgBoxXWinner();
        }

        // If 3 Os are a 3 points of a line from the top right till the buttom of the left side
        if (btn[2][0].getText() == "O" & btn[1][1].getText() == "O" & btn[0][2].getText() == "O") {
            msgBoxOWinner();
        }

        // If there are no winners
        if (getN() == 9) {
            msgBoxNoWinner();
        }
    }

    /**
     * Exit Game
     */
    private void exitGameNow() {
        System.exit(0);
    }

    /**
     * Load New Game
     *
     * <ol>
     *      <li>Set the Counter to 0 {@link #setN(Integer)}</li>
     *      <li>Set Player Names {@link #setPlayerNames(String, String)}</li>
     *      <li>Init. new 2d Object {@link #data} (empty one). The data object is used to store the movements on it to get it later back as {@link #loadData} loadData object</li>
     *      <li>As new player the X start always firstly</li>
     *      <li>ReEnable all buttons</li>
     *      <li>Clear the movements list</li>
     *      <li>Remove all button texts and set it to "" </li>
     * </ol>
     */
    private void loadNewGame() {
        setN(0);
        undoGame.setEnabled(false);
        setPlayerNames("Player X", "Player O");
        data = new Object[3][3];
        playerName.setText("Now Playing: " + getXPlayerName());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setEnabled(true);
                lblLogModel.clear();
                btn[i][j].setText("");
            }
        }
    }

    /**
     * Set Player Name
     *
     * @param xPlayer String
     * @param oPlayer String
     */
    private void setPlayerNames(String xPlayer, String oPlayer) {
        this.oPlayerName = oPlayer;
        this.xPlayerName = xPlayer;
    }

    /**
     * Get Player X Name
     *
     * @return String
     */
    private String getXPlayerName() {
        return xPlayerName;
    }

    /**
     * Get Player O Name
     *
     * @return String
     */
    private String getOPlayerName() {
        return oPlayerName;
    }

    /**
     * X or O
     * {@link #getOPlayerName()} or {@link #getXPlayerName()}
     *
     * @return String
     */
    private String XorO() {
        if (n == 1 || n == 3 || n == 5 || n == 7 || n == 9) {
            playerName.setText("Now Playing: " + getOPlayerName());
            return "X";
        } else {
            playerName.setText("Now Playing: " + getXPlayerName());
            return "O";
        }
    }


    /**
     * Add X or O:
     * <ol>
     *      <li>Add to the List of Movements using {@link #setPosX(int)}, {@link #setPosY(int)} and the Model {@link #lblLogModel}</li>
     *      <li>Init. data {@link #data} to get the value of the counter n {@link #getN()}</li>
     * </ol>
     *
     * @param posX Integer: X Axis
     * @param posY Integer: Y Axis
     */
    private void addXO(Integer posX, Integer posY) {
        setPosX(posX);
        setPosY(posY);
        data[posX][posY] = getN();
        lblLogModel.add(getN() - 1, "Move n " + getN() + ":    ( " + posX + ", " + posY + " )" + "    " + XorO());
    }

    /**
     * Get X Position
     *
     * @return Integer
     */
    private int getPosX() {
        return posX;
    }

    /**
     * Set X Position
     *
     * @param posX Integer
     */
    private void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Get Y Position
     *
     * @return Integer
     */
    private int getPosY() {
        return posY;
    }

    /**
     * Set Y Position
     *
     * @param posY Integer
     */
    private void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Write a File as XML:
     * <ol>
     *      <li>Use new XML Encoder and File Output Stream to save this object {@link #data} to a file</li>
     * </ol>
     *
     * @param obj  Object like {@link #data}
     * @param file String File Name and Path: Here, it is "recources/data.xml"
     * @throws FileNotFoundException File Not Found Exception
     */
    private void writeXML(Object obj, String file) throws FileNotFoundException {
        XMLEncoder xenc = new XMLEncoder(new FileOutputStream(file));
        xenc.writeObject(obj);
        xenc.close();
    }

    /**
     * Save Game as XML:
     * <p>
     * Write a File as XML {@link #writeXML(Object, String)}, Object ist {@link #data}
     */
    private void saveGameAsXML() {
        try {
            writeXML(data, "recources/data.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a XML File
     * <ol>
     *      <li>Use new XML Decoder and File Input Stream to save this object {@link #loadData} to a file</li>
     * </ol>
     *
     * @param file String File Name and Path: Here, it is "recources/data.xml"
     * @return obj  Object like {@link #loadData}
     * @throws FileNotFoundException File Not Found Exception
     */
    private Object readXML(String file) throws FileNotFoundException {
        XMLDecoder xdec = new XMLDecoder(new FileInputStream(file));
        Object obj = xdec.readObject();
        xdec.close();
        return obj;
    }

    /**
     * Open Saved Game:
     *
     * <ol>
     *      <li>Load New Game {@link #loadNewGame()}</li>
     *      <li>Read the game data from the file on "recources/data.xml" using the Object {@link #loadData} and the Method {@link #readXML(String)}</li>
     * </ol>
     */
    private void openGameFromXML() {
        try {
            loadNewGame();
            loadData = (Object[][]) readXML("recources/data.xml");
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    if (loadData[a][b] == null) {
                        loadData[a][b] = 0;
                    } else {
                        setN(getN() + 1);
                        addXO(a, b);
                        btn[a][b].setEnabled(false);
                        btn[a][b].setText(XorO());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
