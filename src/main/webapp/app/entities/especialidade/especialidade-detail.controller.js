(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('EspecialidadeDetailController', EspecialidadeDetailController);

    EspecialidadeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Especialidade'];

    function EspecialidadeDetailController($scope, $rootScope, $stateParams, previousState, entity, Especialidade) {
        var vm = this;

        vm.especialidade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:especialidadeUpdate', function(event, result) {
            vm.especialidade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
