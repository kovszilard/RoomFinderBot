package szilard

import org.jsoup.Jsoup

/**
  * Created by szilard on 2016.04.22..
  */
object Main extends App {

  val mainStite = Jsoup.connect("https://www.wg-gesucht.de/en/wg-zimmer-in-Berlin.8.0.0.0.html").execute()

  val document = Jsoup.connect("https://www.wg-gesucht.de/en/wg-zimmer-in-Berlin.8.0.0.0.html")
    .data("filterID", "")
    .data("ad_type", "0")
    .data("stadt_key", "8")
    .data("filter", "1")
    .data("js_active", "1")
    .data("changesth", "0")
    .data("ortfilter", "")
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
    .data("umkreis_lat", "")
    .data("umkreis_lng", "")
    .data("umkreis_treffer", "")
    .data("umkreis_auswahl", "")
    .data("umkreis_anz", "")
    .data("umkreis_strasse", "")
    .data("umkreis_radius", "")
    .data("bewohner_geschlecht", "0")
    .data("geschlecht", "0")
    .data("rauchen", "0")
    .data("alter", "")
    .data("wgsize_min", "")
    .data("wgsize_max", "")
    .data("zeitraum", "0")
    .data("tausch", "0")
    .data("zimmer_min", "0")
    .data("zimmer_max", "0")
    .data("haustier", "0")
    .data("moebliert", "0")
    .cookies(mainStite.cookies())
    .post()

  println(document.getElementById("table-compact-list").getElementsByTag("tr"))
  println(document.getElementById("table-compact-list").getElementsByTag("tr").size())
}
