function desenhaGrafico() {
    $("#grafico-linhas").highcharts({
        title: {
            text: "Evolução da capacidade dos dispositivos de armazenamento (em megabytes)",
        },
        xAxis: {
            categories: ["Disquete", "CD", "DVD", "Blu-Ray", "Cartão SD", "PEN Drive",
                "Fita Magnética", "SSD", "HDD"]
        },
        series: [{
            name: "Dispositivos de armazenamento",
            data: [1.44, 700, 8192, 25600, 49152, 65536, 409600, 1048576, 2194304]
        }]
    });
};

function desenhaGraficoBarras() {
    $("#grafico-linhas").highcharts({
        chart: {
            type: "column"
        },
        title: {
            text: "Evolução da capacidade dos dispositivos de armazenamento (em megabytes)",
        },
        xAxis: {
            categories: ["Disquete", "CD", "DVD", "Blu-Ray", "Cartão SD", "PEN Drive",
                "Fita Magnética", "SSD", "HDD"]
        },
        series: [{
            name: "Dispositivos de armazenamento",
            data: [1.44, 700, 8192, 25600, 49152, 65536, 409600, 1048576, 3194304]
        }]
    });
};
