(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ConvenioMySuffixDetailController', ConvenioMySuffixDetailController);

    ConvenioMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Convenio'];

    function ConvenioMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Convenio) {
        var vm = this;

        vm.convenio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:convenioUpdate', function(event, result) {
            vm.convenio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
