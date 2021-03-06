package szilard

import com.typesafe.scalalogging.LazyLogging
import org.jsoup.Jsoup

import collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.util.Try

/**
  * Created by szilard on 2016.04.22..
  */
object Main extends App with LazyLogging {
  val checkInterval = 60000

  def getAds: List[String] = {

    val mainStite = Jsoup.connect("https://www.wg-gesucht.de/en/wg-zimmer-in-Berlin.8.0.0.0.html").timeout(10000).execute()

    val document = Jsoup.connect("https://www.wg-gesucht.de/en/wg-zimmer-in-Berlin.8.0.0.0.html")
      .data("filterID", "")
      .data("ad_type", "0")
      .data("stadt_key", "8")
      .data("filter", "1")
      .data("js_active", "1")
      .data("changesth", "1")
      .data("ortfilter", "umkreis")
      .data("filterAlert", "")
      .data("filterName", "")
      .data("rubrik", "0")
      .data("stadtmanuel", "Berlin")
      .data("mietart", "0")
      .data("min_groesse", "")
      .data("max_miete", "")
      .data("frei_ab", "")
      .data("hidden_toleranz_tage_ab", "")
      .data("frei_bis", "")
      .data("hidden_toleranz_tage_bis", "")
      .data("umkreis_lat", "52.4407709")
      .data("umkreis_lng", "13.44450710000001")
      .data("umkreis_treffer", "[{\"address\":\"Neukölln, Berlin, Németország\",\"lat\":52.4407709,\"lng\":13.44450710000001}]")
      .data("umkreis_auswahl", "1")
      .data("umkreis_anz", "")
      .data("umkreis_strasse", "Neukölln, Berlin, Németország")
      .data("umkreis_radius", "10000")
      .data("bewohner_geschlecht", "0")
      .data("geschlecht", "0")
      .data("rauchen", "0")
      .data("alter", "")
      .data("wgsize_min", "0")
      .data("wgsize_max", "0")
      .data("zeitraum", "0")
      .data("tausch", "0")
      .data("zimmer_min", "0")
      .data("zimmer_max", "0")
      .data("haustier", "0")
      .data("moebliert", "0")
      .cookies(mainStite.cookies())
      .timeout(10000)
      .post()

    val tbody = document.getElementById("table-compact-list").getElementsByTag("tbody")

    val usefulRows = tbody.select("tr:gt(1)")

    usefulRows.map(e => e.attr("adid")).toList
  }

  logger.info("Initializing known ads...")

  val knownAds: ListBuffer[String] = new ListBuffer[String]()
  val initialAds = Try(getAds)
  if (initialAds.isSuccess) {
    initialAds.get.foreach(knownAds += _)

    logger.info(s"Initial ads list is ready, checking for new ones in every ${checkInterval / 1000} seconds...")

    while (true) {
      Thread.sleep(checkInterval)

      val result = Try(getAds)
      if (result.isSuccess) {
        result.get.foreach {
          newAd =>
            if (!knownAds.contains(newAd)) {
              logger.info(s"\nNew ad found: $newAd")
              knownAds += newAd
            }
        }
      } else {
        logger.error("\nCannot connet to server...", result.get)
      }

    }
  } else {
    logger.error("Cannot connet to server...", initialAds.get)
  }

}
