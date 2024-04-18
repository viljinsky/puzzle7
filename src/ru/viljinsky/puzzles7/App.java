/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.puzzles7;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class Puzzle {

    Iterable<Puzzle> puzzles;
    int col;
    int row;
    Point center;
    Point[] corner = new Point[4];

    int[] dx = {-20, 20, 20, -20};
    int[] dy = {-20, -20, 20, 20};

    public Puzzle(Iterable<Puzzle> puzzles, int col, int row) {
        this.puzzles = puzzles;
        this.col = col;
        this.row = row;
    }

    // rotate to angle 
    public void rotate(double angle) {
    }

    // move to point
    public void move(Point point) {
        this.center = point;
        for (int i = 0; i < 4; i++) {
            corner[i] = new Point(center.x + dx[i], center.y + dy[i]);
        }
    }

    public void move(int x, int y) {
        move(new Point(x + 20, y + 20));
    }

    private void cross(Graphics g, Color color, Point p) {
        g.setColor(color);
        g.drawLine(p.x - 2, p.y, p.x + 2, p.y);
        g.drawLine(p.x, p.y - 2, p.x, p.y + 2);
    }

    private void rectangle(Graphics g, Color color, Point p) {
        g.setColor(color);
        g.drawRect(p.x - 2, p.y - 2, 5, 5);
    }

    public void paint(Graphics g) {
        for (Point p : corner) {
            rectangle(g, Color.lightGray, p);
        }
        cross(g, Color.blue, center);

    }

    public void recalck() {
        for (int i = 0; i < 4; i++) {
            dx[i] = corner[i].x - center.x;
            dy[i] = corner[i].y - center.y;
        }
    }

    @Override
    public String toString() {
        return String.format("puzzle %d %d ", col, row);
    }

}

class PuzzleAdapter extends MouseAdapter {

    Component parent;
    Iterable<Puzzle> puzzles;
    ArrayList<Point> selected = new ArrayList<>();

    public PuzzleAdapter(Component parent, Iterable<Puzzle> puzzles) {
        this.parent = parent;
        this.puzzles = puzzles;
        parent.addMouseListener(this);
        parent.addMouseMotionListener(this);
        parent.addMouseWheelListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX() - 2, e.getY() - 2, 5, 5);
        for (Puzzle puzzle : puzzles) {
            if (r.contains(puzzle.center)) {
                selected.add(puzzle.center);
                onCenterClick(puzzle);
            }
            for (Point c : puzzle.corner) {
                if (r.contains(c)) {
                    selected.add(c);
                    onCornerClick(puzzle, c);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Point p : selected) {
            p.x += e.getX() - p.x;
            p.y += e.getY() - p.y;
        }

        for (Puzzle puzzle : puzzles) {
            puzzle.recalck();
            puzzle.move(puzzle.center);
        }

        selected.clear();
        parent.repaint();
    }

    public void onCornerClick(Puzzle puzzle, Point corner) {
        System.out.println("cornerClick " + puzzle + " " + corner);
    }

    public void onCenterClick(Puzzle puzzle) {
        System.out.println("centerClic " + puzzle);
    }

}

/**
 *
 * @author viljinsky
 */
public class App extends Base {

    int columns = 10;
    int rows = 10;

    ArrayList<Puzzle> puzzles = new ArrayList<>();

    PuzzleAdapter adapter = new PuzzleAdapter(this, puzzles);

    public App() {
        title = "App";

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                Puzzle puzzle = new Puzzle(puzzles, col, row);
                puzzle.move(col * 40, row * 40);
                puzzles.add(puzzle);
            }
        }
        setPreferredSize(new Dimension(columns * 40, rows * 40));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Puzzle puzzle : puzzles) {
            puzzle.paint(g);
        }
    }

    public static void main(String[] args) {
        new App().showInFrame(null);
    }

}
