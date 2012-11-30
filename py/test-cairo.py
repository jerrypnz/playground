#! /usr/bin/env python
# -*- coding: utf-8 -*-

import pygtk
pygtk.require('2.0')
import gtk, gobject, cairo
import math

# Create a GTK+ widget on which we will draw using Cairo
class Screen(gtk.DrawingArea):

    # Draw in response to an expose-event
    __gsignals__ = { "expose-event": "override" }

    def __init__(self):
        super(Screen, self).__init__()
        self.set_size_request(500, 400)

    # Handle the expose-event by drawing
    def do_expose_event(self, event):

        # Create the cairo context
        cr = self.window.cairo_create()

        # Restrict Cairo to the exposed area; avoid extra work
        cr.rectangle(event.area.x, event.area.y,
                event.area.width, event.area.height)
        cr.clip()

        self.draw(cr, *self.window.get_size())

    def draw(self, cr, width, height):
        # Fill the background with gray
        data = (((0, 0), 'Sun'),
                ((5, 1), 'Mon'),
                ((6, 2), 'Tue'),
                ((8, 4), 'Wed'),
                ((9, 0), 'Thu'),
                ((5, 7), 'Fri'),
                ((0, 0), 'Sat'),
                )
        renderer = BarGraphRenderer(cr, width, height, data)
        renderer.render()



class BarGraphRenderer(object):
    BG_COLOR = (0.8, 0.8, 0.8)
    BORDER_COLOR = (0.2, 0.2, 0.2)
    FG_COLOR = (1, 1, 1)
    V1_COLOR1 = (0.7, 0.8, 0.7)
    V1_COLOR2 = (0.4, 1.0, 0.3)
    V2_COLOR2 = (0.8, 0.7, 0.7)
    V2_COLOR2 = (1.0, 0.4, 0.3)
    TICK_LEN = 3
    LEGEND_WIDTH = 30
    LEGEND_HEIGHT = 13

    def __init__(self, ctx, width, height, data):
        super(BarGraphRenderer, self).__init__()
        self.ctx = ctx
        self.x_reserv = width / 20
        self.width = width
        self.height = height
        self.data = data

    def rad_gradient(self, color1, color2):
        radial = cairo.RadialGradient(0.2, 0.2, 0.1, 0.5, 0.5, 0.6)
        radial.add_color_stop_rgb(0, *color1)
        radial.add_color_stop_rgb(1, *color2)
        return radial

    def render(self):
        self.v1_gradient = self.rad_gradient(self.V1_COLOR1, self.V1_COLOR2)
        self.v2_gradient = self.rad_gradient(self.V2_COLOR2, self.V2_COLOR2)
        self._calculate()
        self._draw_background(self.ctx)
        self._draw_cordinates(self.ctx)
        self._draw_bars(self.ctx)
        self._draw_legends(self.ctx)

    def _calculate(self):
        self.border_width = 45
        self.graph_width = self.width - 2 * self.border_width
        self.graph_height = self.height - 2 * self.border_width
        self.x_max = len(self.data)
        self.y_max = max([max(d) for d, l in self.data]) + 2
        self.x_tick_span = (self.graph_width - self.x_reserv) / self.x_max + 2
        self.x_ticks = range(self.x_reserv, self.graph_width, self.x_tick_span)
        self.y_tick_span = self.graph_height / self.y_max + 2
        self.y_ticks = range(0, self.graph_height, self.y_tick_span)

    def _draw_background(self, cr):
        cr.set_source_rgb(*self.BG_COLOR)
        cr.rectangle(0, 0, self.width, self.height)
        cr.fill()
        cr.set_source_rgb(*self.FG_COLOR)
        cr.rectangle(self.border_width,
                self.border_width,
                self.width - 2 * self.border_width,
                self.height - 2 * self.border_width)
        cr.fill_preserve()
        cr.set_line_width(0.2)
        cr.set_source_rgb(*self.BORDER_COLOR)
        cr.stroke()
        cr.translate(self.border_width, self.border_width)

    def _draw_cordinates(self, cr):
        cr.set_source_rgb(*self.BORDER_COLOR)
        for i, x_tick in enumerate(self.x_ticks):
            cr.move_to(x_tick, self.graph_height)
            cr.line_to(x_tick, self.graph_height - 2)
            cr.move_to(x_tick - self.TICK_LEN, self.graph_height + 10)
            cr.show_text(self.data[i][1])
        cr.move_to(self.graph_width / 2 - 30, self.graph_height + 30)
        cr.show_text('Weekdays')
        for i, y_tick in enumerate(self.y_ticks[1:], 1):
            cr.move_to(0, self.graph_height - y_tick)
            cr.line_to(self.TICK_LEN, self.graph_height - y_tick)
            cr.move_to(self.graph_width, self.graph_height - y_tick)
            cr.line_to(self.graph_width - 3, self.graph_height - y_tick)
            cr.move_to(-20, self.graph_height - y_tick)
            cr.show_text(str(i))
        cr.stroke()
        cr.move_to(-30, self.graph_height / 2 + 30)
        cr.save()
        text_matrix = cr.get_font_matrix()
        text_matrix.rotate(-math.pi * 0.5)
        cr.set_font_matrix(text_matrix)
        cr.show_text('Number of Tomatoes')
        cr.restore()

    def _draw_bars(self, cr):
        bar_width = self.x_tick_span / 3
        for i, ((v1, v2), l) in enumerate(self.data):
            v1_bar_height = max(self.y_tick_span * v1, 1)
            v2_bar_height = max(self.y_tick_span * v2, 1)
            cr.set_source(self.v1_gradient)
            cr.rectangle(self.x_ticks[i] - bar_width,
                    self.graph_height - v1_bar_height,
                    bar_width,
                    v1_bar_height)
            cr.fill_preserve()
            cr.set_source_rgba(0.0, 0.0, 0.0, 0.5)
            cr.stroke()
            cr.set_source(self.v2_gradient)
            cr.rectangle(self.x_ticks[i],
                    self.graph_height - v2_bar_height,
                    bar_width,
                    v2_bar_height)
            cr.fill_preserve()
            cr.set_source_rgba(0.0, 0.0, 0.0, 0.5)
            cr.stroke()

    def _draw_legends(self, cr):
        cr.translate(0, 0)
        cr.rectangle(self.LEGEND_WIDTH,
                self.LEGEND_HEIGHT,
                self.LEGEND_WIDTH,
                self.LEGEND_HEIGHT)
        cr.set_source(self.v1_gradient)
        cr.fill_preserve()
        cr.set_source_rgba(0.0, 0.0, 0.0, 0.5)
        cr.stroke()
        cr.rectangle(self.LEGEND_WIDTH * 5,
                self.LEGEND_HEIGHT,
                self.LEGEND_WIDTH,
                self.LEGEND_HEIGHT)
        cr.set_source(self.v2_gradient)
        cr.fill_preserve()
        cr.set_source_rgba(0.0, 0.0, 0.0, 0.5)
        cr.stroke()
        cr.set_source_rgb(*self.BORDER_COLOR)
        cr.move_to(self.LEGEND_WIDTH * 2 + 10, self.LEGEND_HEIGHT + 10)
        cr.show_text('Finished')
        cr.move_to(self.LEGEND_WIDTH * 6 + 10, self.LEGEND_HEIGHT + 10)
        cr.show_text('Interruptions')



# GTK mumbo-jumbo to show the widget in a window and quit when it's closed
def run(Widget):
    window = gtk.Window()
    window.connect("delete-event", gtk.main_quit)
    widget = Widget()
    widget.show()
    window.add(widget)
    window.present()
    gtk.main()

if __name__ == "__main__":
    run(Screen)
