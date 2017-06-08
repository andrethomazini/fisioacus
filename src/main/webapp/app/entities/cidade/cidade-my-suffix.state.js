(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cidade-my-suffix', {
            parent: 'entity',
            url: '/cidade-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.cidade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cidade/cidadesmySuffix.html',
                    controller: 'CidadeMySuffixController',
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
                    $translatePartialLoader.addPart('cidade');
                    $translatePartialLoader.addPart('estado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cidade-my-suffix-detail', {
            parent: 'cidade-my-suffix',
            url: '/cidade-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.cidade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cidade/cidade-my-suffix-detail.html',
                    controller: 'CidadeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cidade');
                    $translatePartialLoader.addPart('estado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cidade', function($stateParams, Cidade) {
                    return Cidade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cidade-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cidade-my-suffix-detail.edit', {
            parent: 'cidade-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cidade/cidade-my-suffix-dialog.html',
                    controller: 'CidadeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cidade', function(Cidade) {
                            return Cidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cidade-my-suffix.new', {
            parent: 'cidade-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cidade/cidade-my-suffix-dialog.html',
                    controller: 'CidadeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                estado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cidade-my-suffix', null, { reload: 'cidade-my-suffix' });
                }, function() {
                    $state.go('cidade-my-suffix');
                });
            }]
        })
        .state('cidade-my-suffix.edit', {
            parent: 'cidade-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cidade/cidade-my-suffix-dialog.html',
                    controller: 'CidadeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cidade', function(Cidade) {
                            return Cidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cidade-my-suffix', null, { reload: 'cidade-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cidade-my-suffix.delete', {
            parent: 'cidade-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cidade/cidade-my-suffix-delete-dialog.html',
                    controller: 'CidadeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cidade', function(Cidade) {
                            return Cidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cidade-my-suffix', null, { reload: 'cidade-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
