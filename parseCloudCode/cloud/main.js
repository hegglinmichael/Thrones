//upload rating lat and long  
//
//FIELDS(COLUMNS)
// - lat
// - lng
/*
Parse.Cloud.define("test", function(request, response) {
  var text = [];
  text[0] = "a";
  text[1] = "B";
  text[2] = "c";
  var jsonObject = {
    "answer": text,
	"yes": request.params.y
  };

  response.success(jsonObject);
});*/


//tell the user if there are no rated bathrooms within a certain radius
Parse.Cloud.define("getReviews", function(request, response) 
{	
	//query to get data from the QuestionnaireAnswers database
	var query = new Parse.Query("QuestionnaireAnswers");
	
	//array made to take reviews in the correct radius
	var reviewList = [];
	//counter for the for loop
	var count = 0;
	
	var userLat = request.params.userLat;
	var userLng = request.params.userLng;

	//finds all data in the database because no constraints
	query.find({
		//launches if the query is successful
		success: function(results) {
			
			//loop to go through each result
			for(var i = 0; i < results.length; i++)
			{
				//if statement to decide if it is within a 25 mile radius
				if(getDistance(userLat, userLng, results[i].get('lat'), results[i].get('lng')) <= 25)
				{
					//probably want to put in arrayList
					reviewList[count] = results[i];
					//adds to the counter
					count++;
				}
			}
      
			//want to return arrayList of nearby reviews
			var jsonObject = {
				"reviews": reviewList
			};
			
			response.success(jsonObject);
    },
	//launches if the query fails
    error: function() {
      response.error("didn't get list from parse... SHIT!");
    }
  });
});

//this method calculates distance between 2 lat and lng
function getDistance(lat1,lon1,lat2,lon2) {
  var R = 6371; // Radius of the earth in km
  var dLat = deg2rad(lat2-lat1);  // deg2rad below
  var dLon = deg2rad(lon2-lon1); 
  var a = 
    Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    Math.sin(dLon/2) * Math.sin(dLon/2)
    ; 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  var d = R * c * 0.621371; // Distance in miles
  return d;
}

//converts deg to radians
function deg2rad(deg) {
  return deg * (Math.PI/180)
}
