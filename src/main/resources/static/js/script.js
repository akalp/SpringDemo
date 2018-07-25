//d3js
var svg = d3.select("#graph > svg").attr("pointer-events", "all").attr("preserveAspectRatio", "xMinYMin meet");
var definitions = d3.select("#definitions");

var width = svg.node().getBoundingClientRect().width;
var height = svg.node().getBoundingClientRect().height;

// svg objects
var link, node;
// the data - an object with nodes and links
var graph;

var slang = "eng", tlang=new Set(["eng"]);
var languages;
d3.json("/languages").then(function (_langs){
    languages=_langs.langs;
    test();
});

// $("#filter").hide();

d3.select("#search").on("click", function () {
    // $("#filter").show();

    var word = d3.select("#search-box").property("value").toLowerCase();
    d3.select("#searched-word").text(word);

    // load the data
    d3.json("/wordnet/graph?name="+word+"&slang="+slang+"&tlang="+Array.from(tlang).join(",")).then(function (_graph) {
        if (!_graph) return;
        graph = _graph;
        initializeDisplay();
        initializeSimulation();
    });

    //////////// FORCE SIMULATION ////////////

    // force simulator
    var simulation;

    // set up the simulation and event to update locations after each tick
    function initializeSimulation() {
        simulation = d3.forceSimulation();
        simulation.nodes(graph.nodes);
        start();
        initializeForces();
        simulation.on("tick", ticked);
    }

    // values for all forces
    forceProperties = {
        center: {
            x: 0.5,
            y: 0.5
        },
        charge: {
            enabled: true,
            strength: -250,
            distanceMin: 1,
            distanceMax: 1000
        },
        collide: {
            enabled: true,
            strength: .5,
            iterations: 1,
            radius: 5
        },
        forceX: {
            enabled: true,
            strength: .2,
            x: .5
        },
        forceY: {
            enabled: true,
            strength: .2,
            y: .5
        },
        link: {
            enabled: true,
            distance: 80,
            iterations: 1
        }
    };

    // add forces to the simulation
    function initializeForces() {
        // add forces and associate each with a name
        simulation
            .force("link", d3.forceLink())
            .force("charge", d3.forceManyBody())
            .force("collide", d3.forceCollide())
            .force("center", d3.forceCenter())
            .force("forceX", d3.forceX())
            .force("forceY", d3.forceY());
        // apply properties to each of the forces
        updateForces();
    }

    // apply new force properties
    function updateForces() {
        // get each force by name and update the properties
        simulation.force("center")
            .x(width * forceProperties.center.x)
            .y(height * forceProperties.center.y);
        simulation.force("charge")
            .strength(forceProperties.charge.strength * forceProperties.charge.enabled)
            .distanceMin(forceProperties.charge.distanceMin)
            .distanceMax(forceProperties.charge.distanceMax);
        simulation.force("collide")
            .strength(forceProperties.collide.strength * forceProperties.collide.enabled)
            .radius(forceProperties.collide.radius)
            .iterations(forceProperties.collide.iterations);
        simulation.force("forceX")
            .strength(forceProperties.forceX.strength * forceProperties.forceX.enabled)
            .x(width * forceProperties.forceX.x);
        simulation.force("forceY")
            .strength(forceProperties.forceY.strength * forceProperties.forceY.enabled)
            .y(height * forceProperties.forceY.y);
        simulation.force("link")
            .id(function (d) {
                return d.index;
            })
            .distance(forceProperties.link.distance)
            .iterations(forceProperties.link.iterations)
            .links(forceProperties.link.enabled ? graph.links : []);

        // updates ignored until this is run
        // restarts the simulation (important if simulation has already slowed down)
        simulation.alpha(1).restart();
    }

    //////////// DISPLAY ////////////

    // generate the svg objects and force simulation
    function initializeDisplay() {
        var wordtypes=["Noun","Verb","Adjective","Adverb"];
        if(link) { //for clear old data
            d3.select(".nodes").remove();
            d3.select(".links").remove();
            d3.select("#definitions").selectAll(".card").each(function () {
               $(this).show();
            });
            wordtypes.forEach(function (d) {
                definitions.select('#' + d + 's').selectAll("div").remove()
            });
            d3.select("#source-languages").selectAll("option").remove()
            d3.select("#target-languages").selectAll("option").remove()
        }

        // set the data and properties of link lines
        link = svg.append("g")
            .attr("class", "links")
            .selectAll(".link")
            .data(graph.links).enter()
            .append("path")
            .attr("class", function (d) {
                return "link " + d.type;
            });

        // set the data and properties of node circles
        node = svg.append("g")
            .attr("class", "nodes")
            .selectAll(".node")
            .data(graph.nodes).enter()
            .append("g")
            .attr("class", "node")
            .attr("w_id", function (d) {
                if (d.synsetID) return d.synsetID
            })
            .attr("lang", function (d) {
                return d.lang
            })
            .call(d3.drag()
                .on("start", dragstarted)
                .on("drag", dragged)
                .on("end", dragended));

        node.append("circle")
            .attr("class", function (d) {
                return d.type
            })
            .attr("r", forceProperties.collide.radius);

        node.append("text")
            .attr("class", "word-name")
            .attr("dx", 12)
            .attr("dy", 2)
            .text(function (d) {
                return d.word
            });

    }

    // update the display positions after each simulation tick
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
        d3.select('#alpha_value').style('flex-basis', (simulation.alpha() * 100) + '%');
    }

    //////////// UI EVENTS ////////////

    function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
    }

    function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    }

    function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0.0001);
        d.fx = null;
        d.fy = null;
    }

    // update size-related forces
    d3.select(window).on("resize", function () {
        width = svg.node().getBoundingClientRect().width;
        height = svg.node().getBoundingClientRect().height;
        updateForces();
    });

    //definitions
    function initializeDefinitions() {
        graph.nodes.forEach(function (d) {
            definitions.select('#' + d.type + 's')
                .append('div')
                .attr("class", "card-body")
                .attr("w_id", function () {
                    if (d.synsetID) return d.synsetID
                })
                .html("<b>" + d.word + ":</b>" + d.definition);
        });

        d3.select("#definitions").selectAll(".card").each(function () {
            if ($(this).find(".collapse").find(".card-body").length === 0)
                $(this).hide();
        })
    }


    function start() {
        d3.select("#alt-row").style("visibility", "visible");
        test();
        initializeDefinitions();

        //Hover Coloring
        $(".node, .card-body").hover(
            function () {
                $("[w_id=" + $(this).attr('w_id') + "]").addClass("hovered");
            },
            function () {
                $("[w_id=" + $(this).attr('w_id') + "]").removeClass("hovered");
            });


        //Tooltip over svg elements
        $(".node").each(function () {
            $(this).tooltip({
                //Can write any html code to title property
                title: "<br>Language Code: " + $(this).attr("lang") + "</br>Word: " + $(this).text() + "</br>Type:" + $(this).find("circle").attr("class") + "</p>",
                container: 'body',
                placement: 'auto',
                html: true
            });
        });
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
    }
});

d3.select("#filter-submit").on("click", function(){$("#search").click()});

test();
function test(){
    initializeLanguages();
    initializeSelect2();
function initializeLanguages() {
    languages.forEach(function (lang) {
        var str = (tlang.has(lang))? "<option selected>":"<option>";
        var str2 = (slang === lang)? "<option selected>":"<option>";
        $("#source-languages").append(str2 + lang + "</option>");
        $("#target-languages").append(str + lang + "</option>");
    })
}
function initializeSelect2() {
    $('#source-languages').select2({
        placeholder: 'Source Languages',
        theme: 'bootstrap4'
    });

    $('#target-languages').select2({
        placeholder: 'Target Languages',
        theme: 'bootstrap4'
    });

    $('#relationships').select2({
        placeholder: 'Relationships',
        theme: 'bootstrap4'
    });

    $('select').on('change', function (d) {
        // var uldiv = $(this).siblings('span.select2').find('ul');
        // var count = $(this).find('option:selected').length;
        // if (count > 2)
        //     uldiv.html("<li style=\"margin-top: .25rem;\">" + count + " languages selected</li>");


        if (d.target.id === "source-languages") slang = $("#" + d.target.id).select2("data")[0].id;
        else if (d.target.id === "target-languages") {
            var temp = [];
            $("#" + d.target.id).select2("data").forEach(function (l) {
                if (temp.indexOf(l.id) === -1)
                    temp.push(l.id);
            });
            if(temp.length === 0) tlang=new Set(["eng"]);
            else tlang = new Set(temp);
        }
    });

    $('select').on('select2:open', function (d) {
        if ($(".select2-dropdown").find("button").length === 0 && d.target.id !== "source-languages")
            $(".select2-dropdown").prepend("<div><button id=\"" + d.target.id + "-all\" class=\"btn btn-primary buttons\">Select All</button><button id=\"" + d.target.id +
                "-off\" class=\"btn btn-primary buttons\">Unselect All</button></div>");

        $("#" + d.target.id + "-all").on("click", function () {
            $('#' + d.target.id + ' > option').prop("selected", true);
            $("#" + d.target.id).trigger("change");
        });

        $("#" + d.target.id + "-off").on("click", function () {
            $("#" + d.target.id).val(null).trigger("change");
        });
    })
    ;
    $('select').on('select2:closing', function () {
        $(".select2-dropdown").find("div").remove();
    });
}
}