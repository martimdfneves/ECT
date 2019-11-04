probMotorFalhar = linspace(0,1);

probQuedaDoisMotores = nchoosek(2,2) * probMotorFalhar.^2;

probQuedaQuatroMotores = 0;
for i=3:4 
    probQuedaQuatroMotores = probQuedaQuatroMotores + nchoosek(4,i) * probMotorFalhar.^i .* (1-probMotorFalhar).^(4-i);
end

% Gráfico de probabilidade de queda
subplot(1, 2, 1);
plot(probMotorFalhar,probQuedaDoisMotores, probMotorFalhar, probQuedaQuatroMotores);
legend('Avião com dois motores', 'Avião com quatro motores');
title('Probabilidade de avião com dois e quatro motores se despenhar');
xlabel('Probabilidade do motor falhar');
ylabel('Probabilidade de o avião se despenhar');

subplot(1, 2, 2);
loglog(probMotorFalhar,probQuedaDoisMotores, probMotorFalhar,probQuedaQuatroMotores);
legend(''Avião com dois motores', 'Avião com quatro motores');
title('Probabilidade de avião com dois e quatro motores se despenhar');
xlabel('Probabilidade do motor falhar');
ylabel('Probabilidade de o avião se despenhar');