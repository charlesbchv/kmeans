
import com.sun.java.swing.action.AlignCenterAction

import java.awt.event.ActionEvent
import java.awt.{BorderLayout, Color, Font, GridLayout}
import javax.swing.WindowConstants.{EXIT_ON_CLOSE, HIDE_ON_CLOSE}
import javax.swing.{JButton, JFrame, JLabel, JPanel, SwingConstants}
import scala.swing._



object test extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
//title = "First Swing App"
//contents = new Button {
//text = "Click me"




    //private val btnJours = null
    val dem = new KMeans(3, 1500)
    val fen = new Frame()



    fen.title = "Projet 2022 : K-means"
    //fen.bounds = (10,10,700,600)
    fen.size = new Dimension(800,650)
    fen.centerOnScreen()
    fen.peer.setDefaultCloseOperation(EXIT_ON_CLOSE)
    fen.visible = true
    fen.resizable = false



    /*def buildPanelTourner: JPanel = {
      val pan = new JPanel
      pan.setLayout(new GridLayout(-1, 3))
      tourner = new Array[JButton](8)
      var i = 1
      while ( {
        i < tourner.length
      }) {
        val inc = String.valueOf(i)
        btnJours(i) = new JButton(inc)
        pan.add(tourner(i))
        i += 1
      }
      pan.setBorder(BorderFactory.createEtchedBorder)
      pan
    }
    private def initComposants(): Unit = {
      val panPrincipal = new JPanel
      this.add(panPrincipal)
      val b = new BorderLayout
      panPrincipal.setLayout(b)
      panPrincipal.add(tourner, BorderLayout.WEST)

    }*/




    val bouton = new JButton("Clustering")
    bouton.setFont(new Font("Arial", 0, 20))
    bouton.setLayout(new GridLayout(150,150))
    bouton.setPreferredSize(new Dimension(100,50))


    val graph = new Tracer(dem.pointsTab, dem.Clusters)
    var titre = new JLabel("Avant clustering", SwingConstants.CENTER)
    //var xsp = new JLabel("x sepales", SwingConstants.WEST)
    //var ysp = new JLabel("y sepales", SwingConstants.EAST)
    titre.setBackground(Color.WHITE)
    titre.setFont(new Font("Arial", 0, 25))
    titre.setOpaque(true)
    fen.peer.getContentPane.add(titre, BorderLayout.NORTH)
    fen.peer.getContentPane.add(graph, BorderLayout.CENTER)
    fen.peer.getContentPane.add(bouton, BorderLayout.SOUTH)

    bouton.addActionListener((e: ActionEvent) => {
      println("KMeans activé")
      titre.setText("Clusters activés")
      dem.kmeansON()
      graph.setModel(dem.pointsTab)
      graph.setClusters(dem.Clusters)
      graph.repaint()
    })

  }}
