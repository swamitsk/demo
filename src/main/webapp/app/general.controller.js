var app = angular.module('app',[]);

app.controller('GeneralController', ['$scope','CardCRUDService', function ($scope,CardCRUDService) {

    $scope.updateCard = function () {
        CardCRUDService.updateCard($scope.card.cardId,$scope.card.cardHolderName,$scope.card.cardNumber)
            .then(function success(response){
                    $scope.message = 'Card data updated!';
                    $scope.errorMessage = '';
                    $scope.card={};

                },
                function error(response){
                    $scope.errorMessage = 'Error updating Card!';
                    $scope.message = '';
                });
    }

    $scope.BindSelectedData = function (card) {
        $scope.card = card;
    }

    $scope.getCard = function () {
        CardCRUDService.Card($scope.card.cardId)
            .then(function success(response){
                    $scope.card = response.data;
                    $scope.errorMessage = '';
                },
                function error (response ){
                    $scope.message = '';
                    if (response.status === 404){
                        $scope.errorMessage = 'Card not found!';
                    }
                    else {
                        $scope.errorMessage = "Error getting card!";
                    }
                });
    }

    $scope.saveCard = function () {
        if ($scope.card != null && $scope.card.cardHolderName && $scope.card.cardNumber) {
            CardCRUDService.addCard($scope.card.cardHolderName, $scope.card.cardNumber)

                .then(function success(response){
                        $scope.cards = response.data;
                        $scope.message='';
                        $scope.errorMessage = '';
                        $scope.card={};
                    },
                    function error (response ){
                        $scope.message='';
                        $scope.errorMessage = 'Error getting cards!';
                    });
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }
    }

    $scope.deleteCard = function (cardId) {

        CardCRUDService.deleteCard(cardId)
            .then(function success(response){
                    $scope.cards = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error (response ){
                    $scope.message='';
                    $scope.errorMessage = 'Error getting cards!';
                });
    }

    $scope.getAllCards = function () {
        CardCRUDService.getAllCards()
            .then(function success(response){
                    $scope.cards = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error (response ){
                    $scope.message='';
                    $scope.errorMessage = 'Error getting cards!';
                });
    }

}]);

app.service('CardCRUDService',['$http', function ($http) {

    this.getCard = function getCard(cardId){
        return $http({
            method: 'GET',
            url: 'cards/'+cardId
        });
    }

    this.addCard = function addCard(cardHolderName, cardNumber){
        return $http({
            method: 'POST',
            url: 'cards',
            data: {cardHolderName:cardHolderName, cardNumber:cardNumber}
        });
    }

    this.deleteCard = function deleteCard(cardId){
        return $http({
            method: 'DELETE',
            url: 'cards/'+cardId
        })
    }

    this.updateCard = function updateUser(cardId,cardHolderName,cardNumber){
        return $http({
            method: 'PUT',
            url: 'cards/'+cardId,
            data: {cardHolderName:cardHolderName, cardNumber:cardNumber}
        })
    }

    this.getAllCards = function getAllCards(){
        return $http({
            method: 'GET',
            url: 'cards'
        });
    }

}]);