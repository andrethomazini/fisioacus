(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('MedicoDetailController', MedicoDetailController);

    MedicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medico', 'Especialidade'];

    function MedicoDetailController($scope, $rootScope, $stateParams, previousState, entity, Medico, Especialidade) {
        var vm = this;

        vm.medico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:medicoUpdate', function(event, result) {
            vm.medico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
