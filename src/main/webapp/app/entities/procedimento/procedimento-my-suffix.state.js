(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('procedimento-my-suffix', {
            parent: 'entity',
            url: '/procedimento-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.procedimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedimento/procedimentosmySuffix.html',
                    controller: 'ProcedimentoMySuffixController',
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
                    $translatePartialLoader.addPart('procedimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('procedimento-my-suffix-detail', {
            parent: 'procedimento-my-suffix',
            url: '/procedimento-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.procedimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedimento/procedimento-my-suffix-detail.html',
                    controller: 'ProcedimentoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('procedimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Procedimento', function($stateParams, Procedimento) {
                    return Procedimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'procedimento-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('procedimento-my-suffix-detail.edit', {
            parent: 'procedimento-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-my-suffix-dialog.html',
                    controller: 'ProcedimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedimento-my-suffix.new', {
            parent: 'procedimento-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-my-suffix-dialog.html',
                    controller: 'ProcedimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('procedimento-my-suffix', null, { reload: 'procedimento-my-suffix' });
                }, function() {
                    $state.go('procedimento-my-suffix');
                });
            }]
        })
        .state('procedimento-my-suffix.edit', {
            parent: 'procedimento-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-my-suffix-dialog.html',
                    controller: 'ProcedimentoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedimento-my-suffix', null, { reload: 'procedimento-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedimento-my-suffix.delete', {
            parent: 'procedimento-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-my-suffix-delete-dialog.html',
                    controller: 'ProcedimentoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedimento-my-suffix', null, { reload: 'procedimento-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
