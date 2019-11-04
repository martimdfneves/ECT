Set = carregarInformacao('u.data');
J = DistanciaJaccard(Set);
ParesSimilares = getParesSimilares(Set, J, 0.4);