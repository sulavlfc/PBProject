$(document).ready(function() {
    $.ajax({
        type: "GET",
        //url: "C:/Users/Sulav/IdeaProjects/WordCount/test.txt",
        url: "test.txt",
        dataType: "text",
        success: function(data) {processData(data);}
     });
});


function processData(allText) {
    //var record_num = 5;  // or however many elements there are in each row
    var map = new google.maps.Map(document.getElementById('map'), {
                     zoom: 3,
                     center: {lat: 45.490945, lng: 9.140625}
                   });

    var allTextLines = allText.split('\n');
    //var allTextLines = allText.split(',');
    //console.log(allTextLines)
    //var entries = allTextLines[0].split(',');
    //console.log(entries)
    console.log(allTextLines.length)
    for (var j=0; j<allTextLines.length-1; j++) {
                         alldata = allTextLines[j].split(',');
                         console.log(alldata.length)
                         if (alldata.length == 2){
                            var pinColor = "2F76EE"; // a random blue color that i picked
                            var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
                                new google.maps.Size(21, 34),
                                new google.maps.Point(0,0),
                                new google.maps.Point(10, 34));
                            var pinShadow = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_shadow",
                                new google.maps.Size(40, 37),
                                new google.maps.Point(0, 0),
                                new google.maps.Point(12, 35));
                            var marker = new google.maps.Marker({
                            position: {lat:Number(alldata[0]), lng: Number(alldata[1])},
                            map: map,
                            title: 'Zika',
                            icon: pinImage,
                            shadow: pinShadow
                            });

                         }
                         else{
                             var pinColor = "2F76EE"; // a random blue color that i picked
                                                         var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
                                                             new google.maps.Size(21, 34),
                                                             new google.maps.Point(0,0),
                                                             new google.maps.Point(10, 34));
                                                         var pinShadow = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_shadow",
                                                             new google.maps.Size(40, 37),
                                                             new google.maps.Point(0, 0),
                                                             new google.maps.Point(12, 35));
                                                         var marker = new google.maps.Marker({
                                                         position: {lat:Number(alldata[0]), lng: Number(alldata[1])},
                                                         map: map,
                                                         title: 'Zika',
                                                         icon: pinImage,
                                                         shadow: pinShadow


                         });
                         var marker0 = new google.maps.Marker({
                                                                                  position: {lat:Number(alldata[2]), lng: Number(alldata[3])},
                                                                                  map: map,
                                                                                  title: 'Zika',
                                                                                  icon: pinImage,
                                                                                  shadow: pinShadow


                                                  });
                         var marker1 = new google.maps.Marker({
                                                                                  position: {lat:Number(alldata[4]), lng: Number(alldata[5])},
                                                                                  map: map,
                                                                                  title: 'Zika',
                                                                                  icon: pinImage,
                                                                                  shadow: pinShadow


                                                  });
                         var marker2 = new google.maps.Marker({
                                                                                  position: {lat:Number(alldata[6]), lng: Number(alldata[7])},
                                                                                  map: map,
                                                                                  title: 'Zika',
                                                                                  icon: pinImage,
                                                                                  shadow: pinShadow


                                                  });

                         }



}
}

/*                         else {

                                var triangleCoords = [
                                    {lat:Number(alldata[0]), lng: Number(alldata[1])},
                                    {lat:Number(alldata[2]), lng: Number(alldata[3])},
                                    {lat:Number(alldata[4]), lng: Number(alldata[5])},
                                    {lat:Number(alldata[6]), lng: Number(alldata[7])}
                                  ];

                                  var bermudaTriangle = new google.maps.Polygon({
                                      paths: triangleCoords,
                                      strokeColor: '#FF0000',
                                      strokeOpacity: 0.8,
                                      strokeWeight: 2,
                                      fillColor: '#FF0000',
                                      fillOpacity: 0.35
                                    });
                                    bermudaTriangle.setMap(map);
                         }*/
