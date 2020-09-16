$(document).ready(function () {
    var map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM()
          })
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([19.07, 47.31]),
          zoom: 15
        })
      });
});
