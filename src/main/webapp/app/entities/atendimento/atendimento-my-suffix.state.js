(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('atendimento-my-suffix', {
            parent: 'entity',
            url: '/atendimento-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.atendimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atendimento/atendimentosmySuffix.html',
                    controller: 'AtendimentoMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('atendimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('atendimento-my-suffix-detail', {
            parent: 'atendimento-my-suffix',
            url: '/atendimento-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.atendimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atendimento/atendimento-my-suffix-detail.html',
                    controller: 'AtendimentoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('atendimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Atendimento', function($stateParams, Atendimento) {
                    return Atendimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'atendimento-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('atendimento-my-suffix-detail.edit', {
            parent: 'atendimento-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-my-suffix-dialog.html',
                    controller: 'AtendimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atendimento-my-suffix.new', {
            parent: 'atendimento-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-my-suffix-dialog.html',
                    controller: 'AtendimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroCartao: null,
                                desconto: null,
                                valor: null,
                                queixaPrincipal: null,
                                hipoteseDiagnostica: null,
                                dtInicio: null,
                                numeroAutenticador: null,
                                dtTermino: null,
                                quantidadeSessoes: null,
                                observacao: null,
                                cid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('atendimento-my-suffix', null, { reload: 'atendimento-my-suffix' });
                }, function() {
                    $state.go('atendimento-my-suffix');
                });
            }]
        })
        .state('atendimento-my-suffix.edit', {
            parent: 'atendimento-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-my-suffix-dialog.html',
                    controller: 'AtendimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atendimento-my-suffix', null, { reload: 'atendimento-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atendimento-my-suffix.delete', {
            parent: 'atendimento-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-my-suffix-delete-dialog.html',
                    controller: 'AtendimentoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atendimento-my-suffix', null, { reload: 'atendimento-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
