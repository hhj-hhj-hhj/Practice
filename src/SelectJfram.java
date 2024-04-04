import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectJfram extends JFrame implements ActionListener {
    JButton dianMin, gaiLu, nameLable;
    Thread scrollThread;
    String pthto;
    HashMap<String, Double> weights;
    ArrayList<String> names;

    ImageIcon yifei;
    public SelectJfram(ArrayList<String> names, HashMap<String, Double> weights) {

        pthto = "src/yifei.jpg";
        yifei = new ImageIcon(pthto);

        this.names = names;
        this.weights = weights;

        setSize(900, 900);
        setTitle("Random Select");

        this.setAlwaysOnTop(true);
//
//        设置窗口居中
        this.setLocationRelativeTo(null);
//        设置程序关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //        取消默认居中
        this.setLayout(null);

        //        设置窗口可见
        setVisible(true);


//        添加一个名为“点名”的按钮，居中显示在界面
        dianMin = new JButton("点名");
        dianMin.setBounds(400, 550, 100, 50);
        add(dianMin);

        gaiLu = new JButton("概率");
        gaiLu.setBounds(400, 600, 100, 50);
        add(gaiLu);

        nameLable = new JButton("姓名");
        nameLable.setBounds(400, 650, 100, 50);
        add(nameLable);

//        为按钮添加点击事件
        gaiLu.addActionListener(this);
        dianMin.addActionListener(this);
    }

    void updata(String name) {
        double val = weights.get(name);
        val = val / 2;
        weights.put(name, val);
        double each = val / 79;
        for(Map.Entry<String, Double> entry : weights.entrySet()) {
            if(entry.getKey() != name) {
                weights.put(entry.getKey(), entry.getValue() + each);
            }
        }
    }

    String weightSelect() {
        double r = 100 * Math.random();
        for(Map.Entry<String, Double> entry : weights.entrySet()) {
            r -= entry.getValue();
            if(r <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == dianMin) {
            scrollThread = new Thread(() -> {
                int i = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    final int index = i;
                    SwingUtilities.invokeLater(() -> {
                        nameLable.setText(names.get(index));
                        nameLable.repaint();  // 添加这行代码
                    });
                    i = (i + 1) % names.size();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            scrollThread.start();
            String name;
            new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = weightSelect();
                    System.out.println("点名结果：" + name);
                    scrollThread.interrupt();
                    nameLable.setText(name);
                    updata(name);

                    JLabel label = new JLabel(yifei);
                    label.setBounds(168, 0, 564, 715);
                    add(label);

//                  将图片显示在界面左上角
                    SelectJfram.this.getContentPane().add(label);
                    SelectJfram.this.getContentPane().repaint();
                }
            }).start();

        }
        else if(source == gaiLu) {
            weights.forEach((k, v) -> {
                System.out.println(k + " : " + v);
            });
        }
    }
}