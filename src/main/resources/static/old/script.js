// var name;
// $("#searchButton").click(function () {
//     name = $("input[name=search]").val()
// });

// $.urlParam = function (name) {
//     var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
//     return results[1] || 0;
// };
//
// var name = $.urlParam("search");

var name;
$(document).keypress(function (e) {
    var keycode = (e.keyCode ? e.keyCode : e.which);
    if (keycode === 13) {
        $("#searchButton").click();
    }
});

var svg = d3.select('#graph').append('svg').attr('pointer-events', 'all').attr("width", 0).attr("height", 0);

svg.append("svg:defs").selectAll("marker")
    .data(["Synset", "Hyponym", "Hypernym", "Antonym", "Meronym", "Holonym"])
    .enter().append("svg:marker")
    .attr("id", String)
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 5)
    .attr("markerWidth", 6)
    .attr("markerHeight", 6)
    .attr("orient", "auto")
    .append("svg:path")
    .attr("d", "M0,-5L10,0L0,5");

function updateData() {
    svg.selectAll("g").remove();
    svg.selectAll("path").remove();
    name = $("input[name=search]").val();
    if (name) {
        var width = window.innerWidth, height = window.innerHeight;

        var force = d3.forceSimulation()
            .force("link", d3.forceLink().id(function(d) { return d.index; }).distance(80))
            // .force("collide",d3.forceCollide( function(d){return d.r + 8 }).iterations(16) )
            .force("charge", d3.forceManyBody().strength(-150))
            .force("center", d3.forceCenter(width / 2, height / 2))
            .force("y", d3.forceY(0))
            .force("x", d3.forceX(0));

        d3.json("/wordnet/graph?name=" + encodeURIComponent(name), function (error, graph) {
            if (error) return;

            svg.attr("width", width)
                .attr("height", height)
                .attr("preserveAspectRatio", "xMinYMin meet");
            // .attr("viewBox", "0 0 1000 1000");

            var link = svg.selectAll(".link")
                .data(graph.links).enter()
                .append("path")
                .attr("class", function (d) {
                    return "link " + d.type;
                })
                .attr("marker-mid", function (d) {
                    return "url(#" + d.type + ")";
                });

            var node = svg.selectAll(".node")
                .data(graph.nodes).enter()
                .append("g")
                .attr("class", "node")
                .call(d3.drag()
                    .on("start", dragstarted)
                    .on("drag", dragged)
                    .on("end", dragended));

            node.append("circle")
                .attr("class", function (d) {
                    return d.type
                })
                .attr("r", 5);

            node.append("text")
                .attr("class", "word-name")
                .attr("dx", 12)
                .attr("dy", 2)
                .text(function (d) {
                    return d.word
                });


            force.nodes(graph.nodes)
                .on("tick", ticked);
            force.force("link").links(graph.links);


            function ticked() {
                link.attr("d", function (d) {
                    var dx = d.target.x - d.source.x,
                        dy = d.target.y - d.source.y,
                        dr = Math.sqrt(dx * dx + dy * dy);

                    return [
                        "M", d.source.x, d.source.y,
                        "A", dr, dr, 0, 0, 1, d.target.x, d.target.y
                    ].join(" ");
                });

                node.attr("transform", function (d) {
                    return "translate(" + d.x + "," + d.y + ")";
                });
            }

        });

        function dragstarted(d) {
            if (!d3.event.active) force.alphaTarget(0.3).restart();
            d.fx = d.x;
            d.fy = d.y;
        }

        function dragged(d) {
            d.fx = d3.event.x;
            d.fy = d3.event.y;
        }

        function dragended(d) {
            if (!d3.event.active) force.alphaTarget(0);
            d.fx = null;
            d.fy = null;
        }
    }
}
