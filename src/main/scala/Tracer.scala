import com.sun.javafx.sg.prism.NGPolygon

import java.awt.{Color, Graphics}
import javax.swing.JPanel
import scala.collection.mutable.ArrayBuffer

class Tracer(private var model : ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]], private var clusters : ArrayBuffer[ArrayBuffer[Double]]) extends JPanel {

  private final val xMAXIMALE : Int = MaxX() //recherche de la maximale
  private final val yMAXIMALE : Int = MaxY()
  private final val xMINIMALE : Int = MinX()
  private final val yMINIMALE : Int = MinY()

  def MaxX(): Int = { //on cherche le point max X
    var max = 0.0
    for (i <- model.indices) {
      for (points <- model(i)) {
        if (points.head > max) {
          max = points.head
        }
      }
    }
    max.toInt
  }

  def MaxY(): Int = { //maxY
    var max = 0.0
    for (i <- model.indices) {
      for (points <- model(i)) {
        if (points(1) > max) {
          max = points(1)
        }
      }
    }
    max.toInt
  }

  def MinX(): Int = {
    var min = 3141592d
    for (i <- model.indices) {
      for (points <- model(i)) {
        if (points.head < min) {
          min = points.head
        }
      }
    }
    min.toInt
  }

  def MinY(): Int = {
    var min = 3141592d
    for (i <- model.indices) {
      for (points <- model(i)) {
        if (points(1) < min) {
          min = points(1)
        }
      }
    }
    min.toInt
  }

  override def paintComponent(g : Graphics) : Unit  = {
    super.paintComponent(g)
    setBackground(Color.WHITE)
    for (i <- model.indices) {
      for (points <- model(i)) {
        pointsCercle(g, points.head, points(1), Couleurs(i))
      }
    }
    for (clus <- clusters) {
      polygone(g, clus(0), clus(1), Color.CYAN)
    }
  }

  def Couleurs(i : Int) : Color = i%7 match {
    case 0 => {
      Color.RED
    }
    case 1 => {
      Color.BLUE
    }
    case 2 => {
      Color.GREEN
    }
    case 3 => {
      Color.PINK
    }
    //case 4 => Color.MAGENTA
    //case 5 => Color.ORANGE
    //case 6 => Color.YELLOW
    case _ => Color.BLACK
  }

  def setClusters(c : ArrayBuffer[ArrayBuffer[Double]]): Unit = {
    this.clusters = c
  }

  def setModel(m :  ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]]): Unit = {
    this.model = m
  }

  //def GPolygon(g : Graphics ,width : Double,  height : Double, color : Color) : Unit = {
  def polygone(g : Graphics, x : Double, y : Double, color : Color) : Unit = {
    //val xFen = (x + Width/2, y)
    //val xFen = (x + Width, y + Height/2)
    //val xFen = (x + Width/2, y + Height)
    //val xFen = (x, y + Height/2)
    /*new GPolygon : String
    diamond.addVertex(-width/2,0)
    diamond.addVertex(0, -height/2)
    diamond.addVertex(width/2,0)
    diamond.addVertex(0,height/2)
    return diamond*/

    val xFen = this.getSize().width
    val yFen = this.getSize().height
    g.setColor(color)
    val xP = ((x - xMINIMALE) / (xMAXIMALE) * (xFen)).toInt
    val yP = ((((y - yMINIMALE) - yMAXIMALE) / (yMAXIMALE) * (yFen)).toInt).abs

    val a = Array(xP-7,xP,xP+7,xP)
    val b = Array(yP,yP+15,yP,yP-15)

    g.fillPolygon(a, b,4)

  }

  def pointsCercle(g : Graphics, x : Double, y : Double, color : Color) : Unit = {
    val xFen = this.getSize().width
    val yFen = this.getSize().height
    g.setColor(color)
    val xP = ((x - xMINIMALE) / (xMAXIMALE) * (xFen)).toInt
    val yP = ((((y - yMINIMALE) - yMAXIMALE) / (yMAXIMALE) * (yFen)).toInt).abs
    g.fillOval(xP, yP, 7, 7)
  }

}