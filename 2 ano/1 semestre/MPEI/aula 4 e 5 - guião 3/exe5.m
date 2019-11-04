probMotorFalhar = linspace(0,1);

probQuedaDoisMotores = nchoosek(2,2) * probMotorFalhar.^2;

probQuedaQuatroMotores = 0;
for i=3:4 
    probQuedaQuatroMotores = probQuedaQuatroMotores + nchoosek(4,i) * probMotorFalhar.^i .* (1-probMotorFalhar).^(4-i);
end

% Gr�fico de probabilidade de queda
subplot(1, 2, 1);
plot(probMotorFalhar,probQuedaDoisMotores, probMotorFalhar, probQuedaQuatroMotores);
legend('Avi�o com dois motores', 'Avi�o com quatro motores');
title('Probabilidade de avi�o com dois e quatro motores se despenhar');
xlabel('Probabilidade do motor falhar');
ylabel('Probabilidade de o avi�o se despenhar');

subplot(1, 2, 2);
loglog(probMotorFalhar,probQuedaDoisMotores, probMotorFalhar,probQuedaQuatroMotores);
legend(''Avi�o com dois motores', 'Avi�o com quatro motores');
title('Probabilidade de avi�o com dois e quatro motores se despenhar');
xlabel('Probabilidade do motor falhar');
ylabel('Probabilidade de o avi�o se despenhar');