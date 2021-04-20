grammar Gdsii;

@header {
    package mingds;
}

angle: 'ANGLE';
aref: 'AREF';
attrtable: 'ATTRTABLE';
bgnlib: 'BGNLIB';
bgnstr: 'BGNSTR';
boundary: 'BOUNDARY';
box: 'BOX';
boxtype: 'BOXTYPE';
colrow: 'COLROW';
datatype: 'DATATYPE';
elflags: 'ELFLAGS';
endel: 'ENDEL';
endlib: 'ENDLIB';
endmasks: 'ENDMASKS';
endstr: 'ENDSTR';
fonts: 'FONTS';
format: 'FORMAT';
generations: 'GENERATIONS';
header: 'HEADER';
layer: 'LAYER';
libname: 'LIBNAME';
mag: 'MAG';
mask: 'MASK';
node: 'NODE';
nodetype: 'NODETYPE';
path: 'PATH';
pathtype: 'PATHTYPE';
plex: 'PLEX';
presentation: 'PRESENTATION';
propattr: 'PROPATTR';
propvalue: 'PROPVALUE';
reflibs: 'REFLIBS';
sname: 'SNAME';
sref: 'SREF';
stransTag: 'STRANS';
strclass: 'STRCLASS';
string: 'STRING';
strname: 'STRNAME';
text: 'TEXT';
texttype: 'TEXTTYPE';
units: 'UNITS';
width: 'WIDTH';
xy: 'XY';

boundaryElement: boundary elflags? plex? layer datatype xy;
srefElement: sref elflags? plex? sname strans? xy;
arefElement: aref elflags? plex? sname strans? colrow xy;
textElement: text elflags? plex? layer texttype presentation? pathtype? width? strans? xy string;
pathElement: path elflags? plex? layer datatype pathtype? width? xy;
nodeElement: node elflags? plex? layer nodetype xy;
boxElement:  box  elflags? plex? layer boxtype xy;

element: (boundaryElement | pathElement | srefElement | arefElement | textElement | nodeElement | boxElement) property* endel;

structure: bgnstr strname strclass? element+ endstr;
formatType: format | (format mask+ endmasks);

strans:	stransTag mag? angle?;
property: propattr propvalue;

stream: header bgnlib libname reflibs? fonts? attrtable? generations? formatType? units structure+ endlib;
