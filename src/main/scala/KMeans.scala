import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Random.{nextDouble, nextInt}


class KMeans(private val nbClusters : Int, private var nbPoints : Int) {

  var p: ArrayBuffer[ArrayBuffer[Double]] = ArrayBuffer() // initialisation d'un tableau ac les points
  var co_max = 7              //le nombre de points au total
  var Clusters: ArrayBuffer[ArrayBuffer[Double]] = ArrayBuffer() // ici on initialise les clusters
  var pointsTab : ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]] = ArrayBuffer() // on obtient les points coloré
  var suite_kmeans = -1 // Pour itérer le kMeans


  def convertirIris() : Unit = {
    var i = 0
    val filename = "src/main/scala/iris.data" //il faut mettre le lien absoulu sinon s'execute pas

    for (ligne <- Source.fromFile(filename).getLines(); if ligne != "") {
      var pointsT: ArrayBuffer[Double] = ArrayBuffer()
      var tourner = ligne.split(",")


      pointsT.append(tourner(0).toDouble) //pour faire tourner iris il suffit de changer les val par ex tourner (2)
      pointsT.append(tourner(1).toDouble)
      i += 1
      p.append(pointsT)
    }
    pointsTab.append(p)
  }

  convertirIris()  // Initialise des tab avec des valeurs le premier ac les points iris + clusteurs

  def ClustersOn() : Unit = {
    for(i <- 1 to nbClusters) {
      Clusters.append(p(nextInt(p.length - 1)))
    }
  }
  ClustersOn()


  def moyenne() : Array[Double] = {
    var moy = Array.ofDim[Double](2)
    for (i <- p) {
      moy(0) += i.head
      moy(1) += i(1)
    }
    moy(0) /= p.length
    moy(1) /= p.length
    print(moy(0))
    print(moy(1))
    moy
  }

  // pour faire la variance on utilise la moyenne

  def variance(): Array[Double] = {
    var moy = moyenne()
    var vari = Array.ofDim[Double](2)
    for (i <- 0 until 2) {
      for (points <- p) {
        vari(i) += (points(i)-moy(i))*(points(i)-moy(i))
      }
    }
    vari(0) /= p.length
    vari(1) /= p.length
    print(vari(0))
    print(vari(1))
    vari
  }

  // calcule de variance de l'Iris()

  def ecartType() : Array[Double] = {
    var vari = variance()
    var ecart = Array.ofDim[Double](2)
    for (i <- 0 until 2) {
      ecart(i) = math.sqrt(vari(i)).toDouble
    }
    print(ecart(0))
    print(ecart(1))
    ecart
  }

  // ecartTypeIris()


  def affiche() : Unit = {
    for (i <- p) {
      print("|\t " + BigDecimal(i(0)).setScale(2, BigDecimal.RoundingMode.HALF_UP) + " \t | \t " + BigDecimal(i(1)).setScale(2, BigDecimal.RoundingMode.HALF_UP) + " \t |")
    }
  }

  // afficher() // Afficher mes points

  def calc_dist_pts(tab_calc_temp : ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]]): Unit = {
    //calcule de distance entre les points et clusters afin de trouver les plus proches
    for(i <- p.indices) {
      var indice_cluster = 0
      var ecart_cluster = 1000000000d // valeur maximale
      for(j <-Clusters.indices) {
        var racine = math.sqrt({var somme_tab = 0d; for(nEff<-0 until 2) somme_tab+=math.pow(p(i)(nEff)-Clusters(j)(nEff),2); somme_tab})
        if (ecart_cluster > racine) {
          ecart_cluster = racine
          indice_cluster = j
        }
      }
      tab_calc_temp(indice_cluster).append(p(i))
    }
  }

  def recentrer(tab_calc_temp : ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]], recentrer_mesCluster : ArrayBuffer[ArrayBuffer[Double]]): Unit = {
    for(i <- 0 until nbClusters) {
      var temp : ArrayBuffer[Double] = ArrayBuffer()
      for(temporaire <- 0 until 2) temp.append(0)

      for(j <- tab_calc_temp(i).indices)
        for(nEff <- tab_calc_temp(i)(j).indices)
          temp(nEff) += tab_calc_temp(i)(j)(nEff)
      recentrer_mesCluster.append(temp)
    }
    for(i <- 0 until nbClusters) {
      if (tab_calc_temp(i).nonEmpty)
        for(j <- recentrer_mesCluster(i).indices) {
          recentrer_mesCluster(i)(j) = recentrer_mesCluster(i)(j) / tab_calc_temp(i).size
          if (recentrer_mesCluster(i)(j) != Clusters(i)(j)) suite_kmeans = suite_kmeans + 1
        }
      Clusters(i) = recentrer_mesCluster(i)
    }
  }

  def kmeansON(): Unit = {
    var nbIterations = 1 // Pour calculer le nombre d'itérations
    while( suite_kmeans != 0) {
      suite_kmeans = 0
      var tab_calc_temp : ArrayBuffer[ArrayBuffer[ArrayBuffer[Double]]] = ArrayBuffer()
      for(i <- 0 until nbClusters) tab_calc_temp.append(ArrayBuffer());
      calc_dist_pts(tab_calc_temp)
      var recentrer_mesCluster : ArrayBuffer[ArrayBuffer[Double]] = ArrayBuffer()
      recentrer(tab_calc_temp, recentrer_mesCluster)
      if (suite_kmeans != 0) nbIterations += 1
      pointsTab = tab_calc_temp
    }







    println("Nbr d'itération de Kmeans " + nbIterations)
  }

}