let mix = require('laravel-mix');

mix.options({
    processCssUrls: false
});

mix.js('frontend/app.js', 'src/main/webapp/js')
    .version();