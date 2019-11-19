import java.awt.*;

public class ColorManagement {
    Color color;

    public void selectColor(String selection){
        if(selection.equals("Red")){
            color = Color.RED;
        } if(selection.equals("Blue")){
            color = Color.BLUE;
        } if(selection.equals("Green")){
            color = Color.GREEN;
        } if(selection.equals("Purple")){
            color = Color.MAGENTA;
        } if(selection.equals("Orange")){
            color = Color.ORANGE;
        } if(selection.equals("Black")){
            color = Color.BLACK;
        }
    }

    public Color getColor(){
        return color;
    }
}
