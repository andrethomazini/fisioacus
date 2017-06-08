(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('CidadeMySuffixDetailController', CidadeMySuffixDetailController);

    CidadeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cidade'];

    function CidadeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Cidade) {
        var vm = this;

        vm.cidade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:cidadeUpdate', function(event, result) {
            vm.cidade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
