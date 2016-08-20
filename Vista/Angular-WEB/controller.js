var app = angular.module('SegTem', []);

app.config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
]);

app.controller('MainController', function ($scope, $http) {

$http.get("http://localhost:8080/SegTemTest/rest/test/print",

        {

	transformResponse: function (cnv) {

  	var x2js = new X2JS();

  	var aftCnv = x2js.xml_str2json(cnv);

  	return aftCnv;

	}
  })
    .success(function (response) {
    	console.log(response);
	$scope.data = response.tests.test;
  });
});