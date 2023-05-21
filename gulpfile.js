const gulp = require('gulp');
const util = require('util');
const sass = require('gulp-sass')(require('sass'));

gulp.task('sass', function () {
  return gulp.src('src/main/resources/static/scss/*.sass')
    .pipe(sass())
    .pipe(gulp.dest('src/main/resources/static/css'));
});

gulp.task('watch', function () {
  gulp.watch('src/main/resources/static/scss/*.sass', gulp.series('sass'));
});

gulp.task('default', gulp.series('sass', 'watch'));
