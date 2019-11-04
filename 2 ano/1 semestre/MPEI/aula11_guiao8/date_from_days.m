function [month, day] = date_from_days(days)

months = {'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', ...
            'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'};
 
daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

daysInYear = cumsum(daysInMonth);

monthIndex = find(days < daysInYear, 1);
month = months{monthIndex};

if (monthIndex > 1)
    day = days - daysInYear(monthIndex - 1);
else
    day = days;
end
 
end