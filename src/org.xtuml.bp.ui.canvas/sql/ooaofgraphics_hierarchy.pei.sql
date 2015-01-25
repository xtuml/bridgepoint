-- Graphical Data
INSERT INTO T_TNS VALUES (1, 'metadata/Model.gif',            'GD_MD',   'Graphical Model',   '', '', '',                   -1, '');
INSERT INTO T_TNS VALUES (2, 'metadata/FillColor.gif',        'STY_FCS', 'Fill Color',        '', '', 'Fill Color',                   -1, '');
INSERT INTO T_TNS VALUES (3, 'metadata/LineColor.gif',        'STY_LCS', 'Line Color',        '', '', 'Line Color',                   -1, '');
INSERT INTO T_TNS VALUES (4, 'metadata/GraphicalElement.gif', 'GD_GE',   'Graphical Element', '', '', 'Graphical Elements', -1, '');
INSERT INTO T_TNS VALUES (5, 'metadata/Shape.gif',            'GD_SHP',  'Shape',             '', '', 'Shape',                   -1, '');
INSERT INTO T_TNS VALUES (6, 'metadata/Connector.gif',      'GD_CON',  'Connector',         '', '', 'Connector',                   -1, '');
INSERT INTO T_TNS VALUES (7, 'metadata/GraphElement.gif',   'DIM_GE',  'Positional Data',   '', '', 'Positional Data',                   -1, '');
INSERT INTO T_TNS VALUES (8, 'metadata/GraphNode.gif',         'DIM_ND',  'Size Data',         '', '', 'Size Data',                   -1, '');
INSERT INTO T_TNS VALUES (9, 'metadata/Bendpoint.gif',        'DIM_WAY', 'Bendpoint',         '', '', 'Bendpoints',                   -1, '');
INSERT INTO T_TNS VALUES (10, 'metadata/FloatingText.gif',        'GD_CTXT', 'Floating Text',         '', '', 'Floating Text',                   -1, '');
INSERT INTO T_TNS VALUES (11, 'metadata/Graphconnector.gif',        'DIM_CON', 'Anchor Point',         '', '', 'Anchor Points',                   -1, '');
INSERT INTO T_TNS VALUES (12, 'metadata/Layer.gif',        'GD_LAY', 'Layer',         '', '', 'Layers',                   -1, '');
INSERT INTO T_TNS VALUES (13, 'metadata/GraphicalElement.gif',        'GD_GLAY', 'Element In Layer',         '', '', 'Elements In Layer',                   -1, '');

INSERT INTO T_TPS VALUES (1, 1, 2, '->STY_S[R402]->STY_FCS[R400]', '', false);
INSERT INTO T_TPS VALUES (2, 1, 4, '->GD_GE[R1]', '', false);
INSERT INTO T_TPS VALUES (3, 4, 5, '->GD_SHP[R2]', '', false);
INSERT INTO T_TPS VALUES (4, 4, 6, '->GD_CON[R2]', '', false);
INSERT INTO T_TPS VALUES (5, 5, 2, '->GD_GE[R2]->STY_S[R401]->STY_FCS[R400]', '', false);
INSERT INTO T_TPS VALUES (6, 6, 2, '->GD_GE[R2]->STY_S[R401]->STY_FCS[R400]', '', false);
INSERT INTO T_TPS VALUES (7, 6, 3, '->GD_GE[R2]->STY_S[R401]->STY_LCS[R400]', '', false);
INSERT INTO T_TPS VALUES (8, 5, 3, '->GD_GE[R2]->STY_S[R401]->STY_LCS[R400]', '', false);
INSERT INTO T_TPS VALUES (9, 5, 7, '->DIM_ND[R19]->DIM_GE[R301]', '', false); 
INSERT INTO T_TPS VALUES (10, 5, 8, '->DIM_ND[R19]', '', false);
INSERT INTO T_TPS VALUES (11, 6, 9, '->DIM_ED[R20]->DIM_WAY[R319]', '', false);
INSERT INTO T_TPS VALUES (12, 6, 4, '->DIM_ED[R20]->DIM_CON[R320]->DIM_GE[R311]->GD_GE[R23]', 'Source Element', false);
INSERT INTO T_TPS VALUES (13, 6, 4, '->DIM_ED[R20]->DIM_CON[R321]->DIM_GE[R311]->GD_GE[R23]', 'Target Element', false);
INSERT INTO T_TPS VALUES (14, 6, 10, '->GD_CTXT[R8]', '', false);
INSERT INTO T_TPS VALUES (15, 5, 10, '->GD_CTXT[R27]', '', false);
INSERT INTO T_TPS VALUES (16, 10, 7, '->DIM_ND[R19]->DIM_GE[R301]', '', false);
INSERT INTO T_TPS VALUES (17, 10, 8, '->DIM_ND[R19]', '', false);
INSERT INTO T_TPS VALUES (18, 5, 11, '->DIM_ND[R19]->DIM_GE[R301]->DIM_CON[R311]', '', false);
INSERT INTO T_TPS VALUES (19, 6, 11, '->DIM_ED[R20]->DIM_GE[R301]->DIM_CON[R311]', '', false);
INSERT INTO T_TPS VALUES (20, 1, 12, '->GD_LAY[R34]', '', false);
INSERT INTO T_TPS VALUES (21, 12, 13, '->GD_GLAY[R35]', '', false);

-- The entries below specify the data association between tree elements, it captures
-- the underlying data's parent/child association as the tree entries above may
-- include multiple elements to reach a tree child (for display purposes)
--Below is the corresponding T_TPS entry 
--INSERT INTO T_DAS VALUES (12, 'DIM_GE', 'DIM_CON', '->DIM_CON[R311]', 'self');
--INSERT INTO T_DAS VALUES (13, 'DIM_GE', 'DIM_CON', '->DIM_CON[R311]', 'self');