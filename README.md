# `Orthodrome Calculator`

Calculator for the orthodrome (great circle) and loxodrome parameters for the navigation 
purposes.

## Previous project

This project is being built as a successor of my previous JavaFX project, available
in my other [repository](https://github.com/pChocz/orthodrome-calculator-javafx).
As for now, Spring project is not yet finished and the current progress is described
below. If you would like to get working copy of the `Orthodrome Calculator`, please
use working JavaFX application from the link mentioned above.

## Current project

### Current progress (Milestones)

- [x] Complete Spring on-line course :)
- [x] Set up the Spring project (initial)
- [x] Initialize core and web modules
- [x] Create basic HTML template for the home page
- [x] Link the project with the GitHub repository
- [ ] Implement and align logic code from the previous JavaFX project
- [ ] Implement Thymeleaf template corresponding to HTML template
- [ ] Implement decent internationalization properties (`EN` and `PL`)
- [ ] Link logic code to the Thymeleaf template
- [ ] Set up automatic tests and confirm that application is working
- [ ] Deploy a project to an external server
- [ ] Additional milestone: Merge both (JavaFX and Spring) projects together
      so they can share the same CORE module

### Theory first

Orthodrome is a part of the great circle that crosses any pair of the points on the sphere.
Through any two points on a sphere (that are not directly opposite each other), there is 
a unique great circle. The length of the shorter arc of such great circle is so called
great-circle distance (or orthodromic distance) and is the shortest path between two 
points on the surface of the sphere. On below picture, the orthodromic path between 
points `A` and `B` has been marked as `d` side of the spherical triangle.

<img src="/pictures/example.svg" width="400">

In case of antipodal points (directly opposite to each other) there exist infinitely many
great circles. For such cases there are also infinitely many shortest paths equal to the
half of the sphere circumference.

### Why I created the application

Main reason for starting the project was to confirm programmatically that results of my
hand calculations are correct. There are lot of web applications available on the
Internet that calculate orthodromic distance and initial/final bearing angles, but
no application performed step-by-step calculation of all the values that appear during
the hand calculation.

Application covers most of the program of the Spherical Trigonometry section
that is introduced at the first semester of Navigation major on the
Gdynia Maritime University. It can be used by the students to verify their 
calculations performed by hand and to spot exact place of an error occurrence,
in case of results discrepancies.

## Getting Started

### What it does?

`Orthodrome Calculator` can be used in order to calculate several orthodrome/loxodrome 
parameters for the navigation purposes:
* All angles (ABC) and sides (abd) of the spherical triangle created from 2 given points and 
the North Pole,
* Orthodrome distance (in angle units as well as Nm/km),
* Orthodrome bearing angles (initial and final, in angle units),
* Orthodrome vertices,
* Loxodrome distance,
* Orthodromic gain.

Application calculates above parameters for the following cases:

`Equator Sail Case` - 
If both points lie on the equator, the orthodrome will contain the equator as well.
In such case calculation is simplified, as the spherical triangle has two
right angles. Sail direction in this case is either straight `E` or straight `W`.

`Meridian Sail Case` - 
If both points lie on the same meridian, the orthodrome contains this meridian as well.
In such case the spherical triangle does not exist, but the calculation is simplified.
Sail direction in this case is either straight `N` or straight `S`.

`Antipodal points` - 
If points are directly opposite to each other, infinitely many orthodromes exist.
In such case application sets the initial bearing angle as `free to choose` and
final bearing angle as `180° - α`.

`General Case` -
If points do not meet any of above conditions, general case algorithm is applied.
In such case all the parameters are calculated normally.

Special cases are checked first. Only if any of following conditions is met:
`Equator Sail Case`, `Meridian Sail Case`, `Antipodal points`, the calculation
is performed based on the `General Case`. In case if several parameters are
not calculable, appropriate information is added in the results window.

#### Data input format

You need to specify co-ordinates of two points in following format:

`POINT` (lat `Angle`° `Minute`' `Side`; long `Angle`° `Minute`' `Side`)

Each point consists of two co-ordinates: latitude and longitude.
* `Angle` value must be integers between:
  * `0` and `90` for latitude
  * `0` and `180` for longitude
* `Minute` value must be doubles between `0.0` and `59.99`
* `Side` value must be:
  * either `N` or `S` for latitude
  * either `E` or `W` for longitude

As an initial step the application performs data validation. If given values do not 
meet defined input format or are the angles/minutes are not numeric, calculation is
not performed and instruction is printed

## Contributing

If you spot any issue or have an idea how to improve the application, please contact 
me via my GitHub account.

## License

This project is licensed under the MIT License, therefore it's free to use and modify,
but comes without warranty of any kind - see the [LICENSE](LICENSE) file for details.
