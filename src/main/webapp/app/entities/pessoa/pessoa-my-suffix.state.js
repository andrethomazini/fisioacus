(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pessoa-my-suffix', {
            parent: 'entity',
            url: '/pessoa-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.pessoa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pessoa/pessoasmySuffix.html',
                    controller: 'PessoaMySuffixController',
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
                    $translatePartialLoader.addPart('pessoa');
                    $translatePartialLoader.addPart('genero');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pessoa-my-suffix-detail', {
            parent: 'pessoa-my-suffix',
            url: '/pessoa-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.pessoa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pessoa/pessoa-my-suffix-detail.html',
                    controller: 'PessoaMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pessoa');
                    $translatePartialLoader.addPart('genero');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pessoa', function($stateParams, Pessoa) {
                    return Pessoa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pessoa-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pessoa-my-suffix-detail.edit', {
            parent: 'pessoa-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pessoa/pessoa-my-suffix-dialog.html',
                    controller: 'PessoaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pessoa', function(Pessoa) {
                            return Pessoa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pessoa-my-suffix.new', {
            parent: 'pessoa-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pessoa/pessoa-my-suffix-dialog.html',
                    controller: 'PessoaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                telefone: null,
                                celular: null,
                                endereco: null,
                                numero: null,
                                complemento: null,
                                cep: null,
                                contato: null,
                                dtNascimento: null,
                                genero: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pessoa-my-suffix', null, { reload: 'pessoa-my-suffix' });
                }, function() {
                    $state.go('pessoa-my-suffix');
                });
            }]
        })
        .state('pessoa-my-suffix.edit', {
            parent: 'pessoa-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pessoa/pessoa-my-suffix-dialog.html',
                    controller: 'PessoaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pessoa', function(Pessoa) {
                            return Pessoa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pessoa-my-suffix', null, { reload: 'pessoa-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pessoa-my-suffix.delete', {
            parent: 'pessoa-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pessoa/pessoa-my-suffix-delete-dialog.html',
                    controller: 'PessoaMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pessoa', function(Pessoa) {
                            return Pessoa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pessoa-my-suffix', null, { reload: 'pessoa-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
