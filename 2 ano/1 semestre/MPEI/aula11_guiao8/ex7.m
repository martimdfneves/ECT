%% (a) 

initialMoney = [100; 200; 30];

T = [ 0.8   0.1   0.05; ...
      0.2   0.6   0.20; ...
      0.0   0.3   0.75];
  
moneyAfter3Trans = T^3 * initialMoney;

fprintf('(a) Dinheiro da Ana apos 3 transiçoes: $%4.2f\n', moneyAfter3Trans(1));
fprintf('(a) Dinheiro do Bernardo apos 3 transiçoes: $%4.2f\n', moneyAfter3Trans(2));
fprintf('(a) Dinheiro da Catarina apos 3 transiçoes: $%4.2f\n\n', moneyAfter3Trans(3));


%% (b) 
moneyAfter365Trans = T^365 * initialMoney;

fprintf('(b) Dinheiro da Ana apos 1 ano: $%4.2f\n', moneyAfter365Trans(1));
fprintf('(b) Dinheiro do Bernardo apos 1 ano: $%4.2f\n', moneyAfter365Trans(2));
fprintf('(b) Dinheiro da Catarina apos 1 ano: $%4.2f\n\n', moneyAfter365Trans(3));

%% (c) 
n = 1;
moneyAfterNTrans = T * initialMoney;
while(1) 
    n = n + 1;
    moneyAfterNTrans = T * moneyAfterNTrans;
    
    if(moneyAfterNTrans(3) > 130)
        break;
    end
end
fprintf('(c) A Catarina vai ter mais que $130 apos %d transiçoes.\n', n);
[month, day] = date_from_days(n+1);
fprintf('(c) Isto é, ela vai ter $%4.2f em %s %dth.\n', moneyAfterNTrans(3), month, day);