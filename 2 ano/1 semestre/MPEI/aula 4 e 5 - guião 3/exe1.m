%%(a)

x=1:6;
px=ones(1,6)/6;
subplot(1,2,1)
stem(x,px)
axis ([0,6.5,0,0.2]);
xlabel('X')
ylabel('Y')

%% (b)

Fx=cumsum([0,px,0]);
subplot(1,2,2)
stairs(0:7,Fx);
axis ([0,7,0,1.1]);
xlabel('X')
ylabel('F_x(x)')